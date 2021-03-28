package edu.regent.shiritoribot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import edu.regent.shiritoribot.discord.ChannelListener;
import edu.regent.shiritoribot.discord.command.BaseShiritoriCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;

import javax.security.auth.login.LoginException;

public class ShiritoriBot {

    public static JDA jda;

    public static void main(String[] args) {


        System.out.println(System.getenv("Discord_Secret_Token"));
        JDABuilder builder = JDABuilder.createDefault(System.getenv("Discord_Secret_Token"));
        //JDABuilder builder = JDABuilder.create(DISCORD_SECRET_TOKEN, GatewayIntent.) todo: setup correct intents
        builder.addEventListeners(buildCommandManager());

        try {
            jda = builder.build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAuthorOf(Message message) {
        return message.getAuthor().getId().equalsIgnoreCase(jda.getSelfUser().getId());
    }


    private static CommandClient buildCommandManager() {
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix("!");
        builder.addCommand(new BaseShiritoriCommand());
        builder.setOwnerId("");
        return builder.build();
    }

}
