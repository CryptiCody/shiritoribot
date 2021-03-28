package edu.regent.shiritoribot.discord.textChannel;

import edu.regent.shiritoribot.ShiritoriBot;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class LobbyController implements SubController {
    private static final String INITIAL_JOIN_TAG = "{shirijoin}";
    private static final String ACTUAL_JOIN_MESSAGE = "Creating game of Shiritori\n React to this message to join";

    private final TextChannel channel;

    private String joinMsgID;


    public LobbyController(TextChannel channel) {
        this.channel = channel;
    }

    public void init() {
        sendInitialJoinMessage();
    }


    public void addPlayer(Member player) {
        channel.sendMessage(player.getEffectiveName() + " added").queue();
    }

    @Override
    public void handleMessage(GuildMessageReceivedEvent event) {
        if(isInitialJoinTag(event)) {
            Message msg = event.getMessage();
            msg.editMessage(ACTUAL_JOIN_MESSAGE).queue();
            msg.addReaction("U+2705").queue();
            joinMsgID = msg.getId();
            return;
        }
    }

    @Override
    public void handleReaction(GuildMessageReactionAddEvent event) {
        if(joinMsgID.equals(event.getMessageId()) && !event.getUserId().equals(event.getJDA().getSelfUser().getId())) {
            addPlayer(event.getMember());
        }
    }



    public void sendInitialJoinMessage() {
        channel.sendMessage(INITIAL_JOIN_TAG).queue();
    }

    private boolean isInitialJoinTag(GuildMessageReceivedEvent event) {
        return ShiritoriBot.isAuthorOf(event.getMessage())
                && event.getMessage().getContentRaw().equals(INITIAL_JOIN_TAG);
    }
}
