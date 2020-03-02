package events;

import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserLeave extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent ev){
        String userId = ev.getMember().getUser().getId();
        String userName = ev.getMember().getUser().getName();
        System.out.println("User " + userName + " has left the server");
        DBUser user = Data.getDBUser(userId);
        
        if(user.getRoleId().length() > 1){
            ev.getGuild().getRoleById(user.getRoleId()).delete().queue();
        }
        Data.removeUserFromDB(userId);
    }
}
