package edu.regent.shiritoribot.discord.textChannel;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class NullController implements SubController {
    @Override
    public void handleMessage(GuildMessageReceivedEvent event) {

    }

    @Override
    public void handleReaction(GuildMessageReactionAddEvent event) {

    }
}
