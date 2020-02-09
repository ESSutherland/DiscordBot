package commands;

import data.Data;
import data.Experience;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        String[] message = e.getMessage().getContentRaw().split( " ");

        if(message[0].equalsIgnoreCase(Data.PREFIX + "color") || message[0].equalsIgnoreCase(Data.PREFIX + "colour")){
            ColorReqCommand.command(e, message);
        }
        else if(message[0].equalsIgnoreCase(Data.PREFIX + "level")){
            LevelCommand.command(e,message);
        }
        else if(message[0].equalsIgnoreCase(Data.PREFIX + "leveltop")){
            LevelTopCommand.command(e, message);
        }
        else{
            if(!e.getMember().getUser().isBot()) {
                Experience.addExp(e, 1);
            }
        }
    }
}
