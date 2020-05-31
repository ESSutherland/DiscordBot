package events;

import commands.MCWhitelistCommand;
import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserLeave extends ListenerAdapter {
    public void onGuildMemberRemove(GuildMemberRemoveEvent ev){
        String userId = ev.getUser().getId();
        String userName = ev.getUser().getName();
        System.out.println("User " + userName + " has left the server");
        DBUser user = Data.getDBUser(userId);

        if(Data.prop.getProperty("adminChannelId").length() > 0){
            ev.getGuild().getTextChannelById(Data.prop.getProperty("adminChannelId")).sendMessage(ev.getUser().getName() + " has left the server.").queue();
        }
        
        if(user.getRoleId() != null){
            ev.getGuild().getRoleById(user.getRoleId()).delete().queue();
        }
        if(user.getMcUsername() != null){
            MCWhitelistCommand.connect();
            MCWhitelistCommand.unWhitelist(user.getMcUsername());
            MCWhitelistCommand.disconnect();
        }
        Data.removeUserFromDB(userId);
        ArrayList<Message> messages = new ArrayList<>();
        MessageHistory history = new MessageHistory(ev.getGuild().getTextChannelById(Data.prop.getProperty("joinChannelId")));
        List<Message> pastMessages = history.retrievePast(20).complete();
        for(Message m: pastMessages){
            if(m.getAuthor().equals(ev.getUser()) && !m.getTimeCreated().plusWeeks(2).isBefore(OffsetDateTime.now())){
                messages.add(m);
            }
        }
        if(messages.size() > 1){
            ev.getGuild().getTextChannelById(Data.prop.getProperty("joinChannelId")).deleteMessages(messages).queue();
        }
        else if(messages.size() == 1) {
            ev.getGuild().getTextChannelById(Data.prop.getProperty("joinChannelId")).deleteMessageById(messages.get(0).getId()).queue();
        }
    }
}
