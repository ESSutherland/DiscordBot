package events;

import data.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteRole extends ListenerAdapter {

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent ev){
        if(ev.getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("colorChangeRoleId")))){
            if(Data.findUserInDB(ev.getMember().getId())){
                ev.getGuild().getRoleById(Data.roleID).delete().queue();
                Data.removeUserFromDB(ev.getMember().getId());
            }
        }
    }
}
