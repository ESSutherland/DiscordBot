package events;

import commands.MCWhitelistCommand;
import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserLeave extends ListenerAdapter {
    public void onGuildMemberRemove(GuildMemberRemoveEvent ev){
        String userId = ev.getUser().getId();
        String userName = ev.getUser().getName();
        System.out.println("User " + userName + " has left the server");
        DBUser user = Data.getDBUser(userId);
        ev.getGuild().getTextChannelById(Data.prop.getProperty("adminChannel")).sendMessage(ev.getUser().getAsMention() + " has left the server.").queue();
        
        if(user.getRoleId() != null){
            ev.getGuild().getRoleById(user.getRoleId()).delete().queue();
        }

        if(user.getMcUsername() != null){
            MCWhitelistCommand.connect();
            MCWhitelistCommand.unWhitelist(user.getMcUsername());
            MCWhitelistCommand.disconnect();
        }
        Data.removeUserFromDB(userId);
    }
}
