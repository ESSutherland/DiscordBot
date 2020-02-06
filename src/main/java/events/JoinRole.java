package events;

import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinRole extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent ev){
        ev.getGuild().addRoleToMember(ev.getMember(), ev.getGuild().getRoleById(Data.prop.getProperty("defaultRole"))).queue();
    }
}
