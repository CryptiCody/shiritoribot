package edu.regent.shiritoribot.discord.textChannel;

import edu.regent.shiritoribot.ShiritoriBot;
import edu.regent.shiritoribot.discord.PlayerStatus;
import edu.regent.shiritoribot.discord.ShiritoriPlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class LobbyController extends ChannelController {
    private static final String INITIAL_JOIN_TAG = "{shirijoin}";
    private static final String START_COMMAND = "!start";
    private static final String CANCEL_COMMAND = "!cancel";
    private static final String ACTUAL_JOIN_MESSAGE = "Creating game of Shiritori\n React to this message to join\n " +
            "type " + START_COMMAND + " once all players have joined or " + CANCEL_COMMAND + " to cancel the game" ;




    private String joinMsgID;
    private Map<Member, ShiritoriPlayer> players = new HashMap<>();


    public LobbyController(TextChannel channel) {
        super(channel);
    }

    @Override
    public void init() {
        sendInitialJoinMessage();
    }


    public void addPlayer(Member member) {
        ShiritoriPlayer p = new ShiritoriPlayer(member);
        p.init();
        p.setStatus(PlayerStatus.ALIVE);
        players.put(member, p);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!isControlledChannel(event.getChannel())) return;

        if(isInitialJoinTag(event)) {
            Message msg = event.getMessage();
            msg.editMessage(ACTUAL_JOIN_MESSAGE).queue();
            msg.addReaction("U+2705").queue();
            joinMsgID = msg.getId();
        } else if(isStartCommand(event)) {
            passControlTo(new ActiveGameController(channel, players.values()));
        } else if(isCancelCommand(event)) {
            passControlTo(new IdleController(channel));
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(!isControlledChannel(event.getChannel())) return;

        if(joinMsgID.equals(event.getMessageId()) &&
                !event.getUserId().equals(event.getJDA().getSelfUser().getId())) {
            addPlayer(event.getMember());
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        if(!isControlledChannel(event.getChannel())) return;

        if(joinMsgID.equals(event.getMessageId()) && players.containsKey(event.getMember())) {
            players.remove(event.getMember()).destroy();
        }
    }

    public void sendInitialJoinMessage() {
        channel.sendMessage(INITIAL_JOIN_TAG).queue();
    }

    private boolean isInitialJoinTag(GuildMessageReceivedEvent event) {
        return ShiritoriBot.isAuthorOf(event.getMessage())
                && event.getMessage().getContentRaw().equals(INITIAL_JOIN_TAG);
    }

    private boolean isStartCommand(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentDisplay().equalsIgnoreCase(START_COMMAND);
    }

    private boolean isCancelCommand(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentDisplay().equalsIgnoreCase(CANCEL_COMMAND);
    }
}
