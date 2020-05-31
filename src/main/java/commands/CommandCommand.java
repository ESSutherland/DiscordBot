package commands;

import data.CommandData;
import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;

public class CommandCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
            if (message.length < 3) {
                CommandEmbed.errorEB(e, "Please use correct format: " + Data.PREFIX + "command {permission flag} {command} {response} - ({}=required). ");
            } else {
                String command = message[2].toLowerCase();
                String commandLevel = message[1];
                String commandMessage = "";
                for (int i = 3; i < message.length; i++) {
                    commandMessage += message[i] + " ";
                }

                if(commandLevel.equalsIgnoreCase("-a") || commandLevel.equalsIgnoreCase("-b")
                        || commandLevel.equalsIgnoreCase("-s") || commandLevel.equalsIgnoreCase("-m")){
                    try {
                        CommandData.addCommand(command, commandMessage, commandLevel);
                        CommandEmbed.successEB(e, "Command " + command + " created.");
                    } catch (SQLException ex) {
                        CommandEmbed.errorEB(e, "Command already exists.");
                    }
                }
                else if(e.getMessage().getMentionedMembers().size() > 0){
                    if(e.getMessage().getMentionedMembers().size() != 1){
                        CommandEmbed.errorEB(e, "Please include one user.");
                    }
                    else{
                        Member member = e.getMessage().getMentionedMembers().get(0);
                        try {
                            CommandData.addCommand(command, commandMessage, member.getId());
                            CommandEmbed.successEB(e, "Command " + command + " created.");
                        } catch (SQLException ex) {
                            CommandEmbed.errorEB(e, "Command already exists.");
                        }
                    }
                }
                else{
                    CommandEmbed.errorEB(e, "Please use a valid permission flag. -a = Everyone, -b = Nitro Boosters, -s = Subscribers, or you can @ a user to make a private command.");
                }
            }
    }
}
