package events;

import data.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserLeave extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent ev){
        String userId = ev.getMember().getUser().getId();
        String userName = ev.getMember().getUser().getName();

        Data.removeUserFromDB(userId);
    }
}
