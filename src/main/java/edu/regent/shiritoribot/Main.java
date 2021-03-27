package edu.regent.shiritoribot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    private static final String DISCORD_SECRET_TOKEN = "ODI1NDQ1NTAzMDU5NjIzOTQ2.YF-CBw.00aTeYb6EdjJQ2xWxbZTXK5duuU";

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //JDABuilder builder = JDABuilder.create(DISCORD_SECRET_TOKEN, GatewayIntent.) todo: setup correct intents
        builder.setToken(DISCORD_SECRET_TOKEN);
        builder.addEventListeners(new HelloScreamer());

        try {
            builder.build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

}
