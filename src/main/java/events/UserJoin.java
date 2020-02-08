package events;

import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent ev){
        String userId = ev.getMember().getUser().getId();
        String userName = ev.getMember().getUser().getName();

        ev.getGuild().addRoleToMember(ev.getMember(), ev.getGuild().getRoleById(Data.prop.getProperty("defaultRole"))).queue();
        System.out.println("Added default role to " + userName);

        Data.addUserToDB(userId, userName);
    }
}
