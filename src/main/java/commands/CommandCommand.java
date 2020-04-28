package commands;

import data.CommandData;
import data.CommandEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;

public class CommandCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
            if (message.length < 3) {
                CommandEmbed.errorEB(e, "Please use correct format: !command <command> <message>");
            } else {
                String command = message[1];
                String commandMessage = "";
                for (int i = 2; i < message.length; i++) {
                    commandMessage += message[i] + " ";
                }
                try {
                    CommandData.addCommand(command, commandMessage);
                    CommandEmbed.successEB(e, "Command " + command + " created.");
                } catch (SQLException ex) {
                    CommandEmbed.errorEB(e, "Command already exists.");
                }
            }
    }
}
