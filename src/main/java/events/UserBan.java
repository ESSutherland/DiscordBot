package events;

import data.Data;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;

import java.util.concurrent.TimeUnit;

public class UserBan extends ListenerAdapter {
    @Override
    public void onGuildBan(GuildBanEvent e) {
        if(Data.prop.getProperty("adminChannelId").length() > 0){
            AuditLogPaginationAction logs = e.getGuild().retrieveAuditLogs().type(ActionType.BAN);
            for (AuditLogEntry entry : logs) {
                String message = entry.getUser().getName() +
                        " has banned " + e.getUser().getName() + " from the server." + ((entry.getReason() != null) ? " Reason: " + entry.getReason(): "");
                e.getGuild().getTextChannelById(Data.prop.getProperty("adminChannelId")).sendMessage(message).queue();
                System.out.println(message);
                break;
                //System.out.println(entry.getUser() + " - " + entry.getReason() + " - " + entry.getTimeCreated());
            }
        }
    }
}
