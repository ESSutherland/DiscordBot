package events;

import commands.MCWhitelistCommand;
import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteRole extends ListenerAdapter {

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent ev){

        System.out.println(ev.getMember().getUser().getName() + " lost role: " + ev.getRoles().toString());

        String userId = ev.getMember().getUser().getId();
        String userName = ev.getMember().getUser().getName();
        if(ev.getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            if(Data.findUserInDB(userId)){
                DBUser user = Data.getDBUser(userId);
                if(user.getRoleId() != null){
                    ev.getGuild().getRoleById(user.getRoleId()).delete().queue();
                    System.out.println("Deleting Nitro Role For " + user.getUserName());
                    Data.updateUserColorInDB(userId, userName, null, null);
                }
            }
        }
        else if(ev.getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("subRoleId")))){
            if(Data.findUserInDB(userId)){
                DBUser user = Data.getDBUser(userId);
                if(user.getMcUsername() != null && !ev.getMember().getRoles().contains(ev.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))){
                    MCWhitelistCommand.connect();
                    MCWhitelistCommand.unWhitelist(userId);
                    MCWhitelistCommand.disconnect();
                    Data.updateMCUserName(null, userId);
                }
            }
        }
    }
}
