package events;

import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AddRole extends ListenerAdapter {

    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e){

        System.out.println(e.getMember().getUser().getName() + " gained role: " + e.getRoles().toString());

    }
}
