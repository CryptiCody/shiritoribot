package edu.regent.shiritoribot.discord.textChannel;

import edu.regent.shiritoribot.ShiritoriBot;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class ChannelController extends ListenerAdapter {
    protected final TextChannel channel;

    public ChannelController(TextChannel channel) {
        this.channel = channel;
    }

    public void passControlTo(ChannelController newController) {
        ShiritoriBot.jda.removeEventListener(this);
        ShiritoriBot.jda.addEventListener(newController);
        newController.init();
    }

    protected boolean isControlledChannel(TextChannel channel) {
        return this.channel.getId().equals(channel.getId());
    }

    protected abstract void init();
}
