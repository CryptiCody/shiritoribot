package edu.regent.shiritoribot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import edu.regent.shiritoribot.discord.ChannelListener;
import edu.regent.shiritoribot.discord.command.BaseShiritoriCommand;
import edu.regent.shiritoribot.game.WordDictionary;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class ShiritoriBot {

    public static JDA jda;

    public static void main(String[] args) {


        System.out.println(System.getenv("Discord_Secret_Token"));
        JDABuilder builder = JDABuilder.createDefault(System.getenv("Discord_Secret_Token"));
        //JDABuilder builder = JDABuilder.create(DISCORD_SECRET_TOKEN, GatewayIntent.) todo: setup correct intents
        builder.addEventListeners(buildCommandManager());
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ROLE_TAGS, CacheFlag.EMOTE);

        try {
            jda = builder.build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAuthorOf(Message message) {
        return message.getAuthor().getId().equalsIgnoreCase(jda.getSelfUser().getId());
    }

    public static WordDictionary getWordDictionary() {
        try {
            return WordDictionary.fromFile(new File(ShiritoriBot.class.getClassLoader().getResource("usa.txt").getFile()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static CommandClient buildCommandManager() {
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix("!");
        builder.addCommand(new BaseShiritoriCommand());
        builder.setOwnerId("");
        return builder.build();
    }

}
