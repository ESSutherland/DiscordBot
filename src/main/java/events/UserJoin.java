package events;

import data.Data;
import data.Modules;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getName();

        System.out.println(userName + " joined the server.");

        try{
            if(Modules.isModuleEnabled("agree")){
                e.getGuild().addRoleToMember(userId, e.getGuild().getRoleById(Data.prop.getProperty("defaultRoleId"))).queue();
            }
            else{
                e.getGuild().addRoleToMember(userId, e.getGuild().getRoleById(Data.prop.getProperty("userRoleId"))).queue();
            }
            Data.addUserToDB(userId, userName);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
