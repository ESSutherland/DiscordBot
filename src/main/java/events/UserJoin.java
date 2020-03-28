package events;

import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getName();

        try{
            e.getGuild().addRoleToMember(userId, e.getGuild().getRoleById(Data.prop.getProperty("defaultRole"))).queue();
            Data.addUserToDB(userId, userName);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Added default role to " + userName);
    }
}
