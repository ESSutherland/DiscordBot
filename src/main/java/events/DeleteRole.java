package events;

import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteRole extends ListenerAdapter {

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent ev){
        if(ev.getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            String userId = ev.getMember().getUser().getId();
            String userName = ev.getMember().getUser().getName();
            if(Data.findUserInDB(userId)){
                ev.getGuild().getRoleById(Data.roleID).delete().queue();
                Data.updateUserColorInDB(userId, userName, "", "");
            }
        }
    }
}
