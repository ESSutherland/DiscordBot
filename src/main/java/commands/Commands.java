package commands;

import data.Data;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        String[] message = e.getMessage().getContentRaw().split( " ");

        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getName();
        if(!Data.findUserInDB(userId)){
            Data.addUserToDB(userId, userName);
        }

        if(message[0].equalsIgnoreCase(Data.PREFIX + "color") || message[0].equalsIgnoreCase(Data.PREFIX + "colour")){
            ColorReqCommand.command(e, message);
        }
    }
}
