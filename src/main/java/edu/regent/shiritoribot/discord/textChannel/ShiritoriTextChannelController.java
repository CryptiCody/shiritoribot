package edu.regent.shiritoribot.discord.textChannel;

import edu.regent.shiritoribot.game.ActiveShiritoriGame;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ShiritoriTextChannelController extends ListenerAdapter {
    private static final String GAME_INIT_MESSAGE = "!shiritori";

    private TextChannel channel;
    private String joinMsgID;
    private ActiveShiritoriGame activeGame;

    private SubController activeSubController = new NullController();

    public ShiritoriTextChannelController(TextChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!isControlledChannel(event.getChannel())) return;

        if(isGameInitMessage(event)) {
            LobbyController lobbyController = new LobbyController(channel);
            activeSubController = lobbyController;
            lobbyController.init();
        } else {
            activeSubController.handleMessage(event);
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(!isControlledChannel(event.getChannel())) return;
        activeSubController.handleReaction(event);
    }




    private boolean isGameInitMessage(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentDisplay().equalsIgnoreCase(GAME_INIT_MESSAGE);
    }

    private boolean isControlledChannel(TextChannel channel) {
        return this.channel.getId().equals(channel.getId());
    }

}
