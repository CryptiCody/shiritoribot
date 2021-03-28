package edu.regent.shiritoribot.discord;

import edu.regent.shiritoribot.ShiritoriBot;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.EmoteManager;
import net.dv8tion.jda.internal.managers.EmoteManagerImpl;
import net.dv8tion.jda.internal.requests.Route;

import javax.annotation.Nonnull;

public class ChannelListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        //if(isControlledChannel(event.getChannel())) {
        if(event.getMessage().getContentDisplay().equalsIgnoreCase("test")) {
            sendGameJoinMessage(event.getChannel());
        }

        //event.getMessage().addReaction("U+2705").queue();
    }



    public void sendGameJoinMessage(TextChannel channel) {
        MessageBuilder msgBuilder = new MessageBuilder();
        msgBuilder.append("Creating game of Shiritori");
        msgBuilder.append("\nReact to this message to join");
        Message msg = msgBuilder.build();
        channel.sendMessage(msg).queue();


        //msg.addReaction("U+2705").queue();
    }



    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        System.out.println("guild message reacted");
    }


}
