package edu.regent.shiritoribot.discord.textChannel;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;

public class IdleController extends ChannelController {
    private static final String GAME_INIT_MESSAGE = "!shiritori";

    public IdleController(TextChannel channel) {
        super(channel);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!isControlledChannel(event.getChannel())) return;

        if(isGameInitMessage(event)) {
            LobbyController lobbyController = new LobbyController(channel);
            passControlTo(lobbyController);
        }
    }



    private boolean isGameInitMessage(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentDisplay().equalsIgnoreCase(GAME_INIT_MESSAGE);
    }


}
