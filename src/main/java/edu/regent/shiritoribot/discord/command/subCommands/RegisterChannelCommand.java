package edu.regent.shiritoribot.discord.command.subCommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import edu.regent.shiritoribot.ShiritoriBot;
import edu.regent.shiritoribot.discord.textChannel.IdleController;


public class RegisterChannelCommand extends Command {
    public RegisterChannelCommand() {
        this.name = "register";
        this.help = "registers this channel for playing Shiritori";
        this.guildOnly = true;
        //this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        ShiritoriBot.jda.addEventListener(new IdleController(event.getTextChannel()));
        event.reply("channel registered!!!!");
    }
}
