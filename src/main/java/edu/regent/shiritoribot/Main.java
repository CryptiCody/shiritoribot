package edu.regent.shiritoribot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getenv("Discord_Secret_Token"));
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //JDABuilder builder = JDABuilder.create(DISCORD_SECRET_TOKEN, GatewayIntent.) todo: setup correct intents
        builder.setToken(System.getenv("Discord_Secret_Token"));
        builder.addEventListeners(new HelloScreamer());

        try {
            builder.build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

}
