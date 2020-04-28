package commands;

import data.CommandData;
import data.CommandEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class DeleteCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message) {
            if (message.length < 2) {
                CommandEmbed.errorEB(e, "Please include a command. ({}=required): !command {command} {response}");
            } else {
                String command = message[1];
                try {
                    if (CommandData.isCommand(command)) {
                        CommandData.removeCommand(command);
                        CommandEmbed.successEB(e, "Command " + command + " removed.");
                    } else {
                        CommandEmbed.errorEB(e, "Command " + command + " does not exist.");
                    }
                } catch (Exception ex) {
                    CommandEmbed.errorEB(e, "Command " + command + " does not exist.");
                }
            }
    }
}
