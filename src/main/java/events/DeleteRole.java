package events;

import data.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteRole extends ListenerAdapter {

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent ev){

        if(ev.getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("colorChangeRoleId")))){
            for(int i = 0; i < Data.userIds.size(); i++){
                if(Data.userIds.get(i).equals(ev.getMember().getId())){

                   Role r = ev.getGuild().getRoleById(Data.roleIds.get(i));

                    Data.roleIds.remove(i);
                    Data.userIds.remove(i);
                    Data.userNames.remove(i);

                    Data.writeData();

                    r.delete().queue();
                    System.out.println(Data.roleIds + " " + Data.userIds + " " + Data.userNames);
                }
            }
        }
    }
}
