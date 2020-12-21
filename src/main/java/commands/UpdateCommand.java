package commands;

import data.CommandData;
import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UpdateCommand {
    public static void command(GuildMessageReceivedEvent e, String[] message){
        if (message.length < 3) {
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): `" + Data.PREFIX + "update {command} {response}` ");
        } else {
            String command = message[1].toLowerCase();
            String commandMessage = "";
            for (int i = 2; i < message.length; i++) {
                commandMessage += message[i] + " ";
            }

            try{
                if(CommandData.isCommand(command)){
                    CommandData.updateCommand(command, commandMessage);
                    CommandEmbed.successEB(e, "Command `" + command + "` has been updated.");
                }
                else{
                    CommandEmbed.errorEB(e, "Command `" + command + "` does not exist.");
                }
            }
            catch (Exception ex){
                CommandEmbed.errorEB(e, "Command `" + command + "` does not exist.");
            }
        }
    }
}
