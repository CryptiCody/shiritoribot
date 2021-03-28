package edu.regent.shiritoribot.discord.textChannel;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent;

public interface SubController {

    public void handleMessage(GuildMessageReceivedEvent event);
    public void handleReaction(GuildMessageReactionAddEvent event);
}
