package commands;

import data.Data;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class PurgeCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        e.getMessage().delete().complete();
        if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))){
            Member member;
            String userId;
            String userName;
            int num;

            if(message.length > 2){
                num = Integer.parseInt(message[1]);
                for(net.dv8tion.jda.api.entities.Member mem: e.getMessage().getMentionedMembers()) {
                    userId = mem.getId();

                    ArrayList<Message> messages = new ArrayList<>();
                    MessageHistory history = new MessageHistory(e.getChannel());
                    List<Message> pastMessages = history.retrievePast(100).complete();
                    for(Message m: pastMessages){
                        if(m.getAuthor().equals(e.getGuild().getMemberById(userId).getUser())){
                            messages.add(m);
                        }

                        if(messages.size() == num){
                            break;
                        }
                    }
                    if(messages.size() > 1){
                        e.getChannel().deleteMessages(messages).queue();
                    }
                    else{
                        e.getChannel().deleteMessageById(messages.get(0).getId()).queue();
                    }
                }
            }
            else if(message.length > 1){
                num = Integer.parseInt(message[1]);

                ArrayList<Message> messages = new ArrayList<>();
                MessageHistory history = new MessageHistory(e.getChannel());
                List<Message> pastMessages = history.retrievePast(100).complete();
                for(Message m: pastMessages){
                    if(m.getAuthor().equals(e.getAuthor())){
                        messages.add(m);
                    }

                    if(messages.size() == num){
                        break;
                    }
                }
                if(messages.size() > 1){
                    e.getChannel().deleteMessages(messages).queue();
                }
                else if (messages.size() == 1){
                    e.getChannel().deleteMessageById(messages.get(0).getId()).queue();
                }
                else{
                }
            }
        }
    }
}
