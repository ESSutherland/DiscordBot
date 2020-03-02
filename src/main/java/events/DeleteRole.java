package events;

import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteRole extends ListenerAdapter {

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent ev){
        if(ev.getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            String userId = ev.getMember().getUser().getId();
            String userName = ev.getMember().getUser().getName();
            if(Data.findUserInDB(userId)){
                DBUser user = Data.getDBUser(userId);
                if(user.getRoleId().length() > 1){
                    ev.getGuild().getRoleById(user.getRoleId()).delete().queue();
                    Data.updateUserColorInDB(userId, userName, "", "");
                }
            }
        }
    }
}
