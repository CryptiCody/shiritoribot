package edu.regent.shiritoribot.discord.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import edu.regent.shiritoribot.discord.command.subCommands.RegisterChannelCommand;

public class BaseShiritoriCommand extends Command {
    public BaseShiritoriCommand() {
        this.name = "shiritori";
        this.aliases = new String[]{"shiri"};
        this.help = "registers this channel for playing Shiritori";
        this.guildOnly = true;
        this.children = new Command[] {new RegisterChannelCommand()};
    }

    @Override
    protected void execute(CommandEvent event) {
    }
}
