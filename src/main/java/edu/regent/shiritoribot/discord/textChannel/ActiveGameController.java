package edu.regent.shiritoribot.discord.textChannel;

import edu.regent.shiritoribot.ShiritoriBot;
import edu.regent.shiritoribot.discord.PlayerStatus;
import edu.regent.shiritoribot.discord.ShiritoriPlayer;
import edu.regent.shiritoribot.game.EliminationException;
import edu.regent.shiritoribot.game.ShiritoriWordValidator;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ActiveGameController extends ChannelController {

    private static long TURN_TIMEOUT_MILLIS = 15_000;

    private List<ShiritoriPlayer> alivePlayers;
    private List<ShiritoriPlayer> deadPlayers;
    private ShiritoriPlayer activePlayer;

    private ShiritoriWordValidator wordValidator;
    private Message nextPlayerDisplayMessage;
    private Thread timeoutThread;

    public ActiveGameController(TextChannel channel, Collection<ShiritoriPlayer> initialPlayers) {
        super(channel);
        this.alivePlayers = new ArrayList<>(initialPlayers);
        this.deadPlayers = new ArrayList<>();
        Collections.shuffle(alivePlayers);
        activePlayer = alivePlayers.get(0); //will be skipped, get(1) will actually be first player to act)
    }

    @Override
    protected void init() {
        clearChannelHistory();
        for(ShiritoriPlayer player : alivePlayers) {
            player.setStatus(PlayerStatus.ALIVE);
        }
        wordValidator = new ShiritoriWordValidator(ShiritoriBot.getWordDictionary());
        nextPlayer();
    }



    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!isControlledChannel(event.getChannel())) return;
        if(ShiritoriBot.isAuthorOf(event.getMessage())) return;
        if(!isHisTurn(event.getMember())) {
            event.getMessage().delete().queue();
            return;
        }

        String chosenWord = event.getMessage().getContentStripped();
        try {
            wordValidator.submitWord(chosenWord);
            activePlayerLives();
            event.getMessage().addReaction("\u2705").queue();
        } catch (EliminationException e) {
            activePlayerDies();
            event.getMessage().addReaction("\u274C").queue();
        }
    }

    private void declareWinner(ShiritoriPlayer player) {
        player.setStatus(PlayerStatus.NO_VISIBLE_STATUS);
        channel.sendMessage(player.getMember().getEffectiveName() + " WINS!").queue();
        close();
    }

    private void close() {
        for(ShiritoriPlayer player : alivePlayers) {
            player.destroy();
        }
        passControlTo(new IdleController(channel));
    }



    private void activePlayerLives() {
        activePlayer.setStatus(PlayerStatus.ALIVE);
        nextPlayer();
    }

    private void activePlayerTimeout() {
        channel.sendMessage("Time's Up!").complete();
        activePlayerDies();
    }

    private void activePlayerDies() {
        activePlayer.setStatus(PlayerStatus.DEAD);
        alivePlayers.remove(activePlayer);
        deadPlayers.add(activePlayer);
        nextPlayer();
    }

    private void nextPlayer() {
        if(alivePlayers.size() == 1) {
            declareWinner(alivePlayers.get(0));
        } else {
            int indexOfNextPlayer = alivePlayers.indexOf(activePlayer) + 1;
            if(indexOfNextPlayer == alivePlayers.size()) {
                activePlayer = alivePlayers.get(0);
            } else {
                activePlayer = alivePlayers.get(indexOfNextPlayer);
            }

            System.out.println("Next Player: " + activePlayer.getName());
            if(nextPlayerDisplayMessage != null) {
                nextPlayerDisplayMessage.delete().complete();
            }
            nextPlayerDisplayMessage = channel.sendMessage("Next Player: " + activePlayer.getName()).complete();

            resetTimeout();
        }
    }

    private void resetTimeout() {
        if(timeoutThread != null) {
            timeoutThread.interrupt();
        }
        timeoutThread = new Thread(() -> {
            try {
                Thread.sleep(TURN_TIMEOUT_MILLIS);
                activePlayerTimeout();
            } catch (InterruptedException e) {
                //timeout cancelled, do nothing
            }
        });
        timeoutThread.start();
    }

    private boolean isHisTurn(Member member) {
        return activePlayer.getMember().equals(member);
    }

}
