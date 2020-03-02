package commands;

import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.List;

public class LoadUsersCommand {
    public static void command(GuildMessageReceivedEvent e, String[] message){
        if(PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
            List<Member> members = e.getGuild().getMembers();
            System.out.println("Loading Users - " + Data.userList.size());
            e.getChannel().sendMessage("> Loading users.").queue();
            boolean userFound = false;
            int count = 1;
            for(Member m : members){
                for(DBUser user : Data.userList){
                    if(user.getUserId().equalsIgnoreCase(m.getUser().getId())){
                        userFound = true;
                    }
                }
                if(!userFound){
                    System.out.println("Adding " + m.getUser().getId() + " - " + m.getUser().getName() + " to DB.");
                    Data.addUserToDB(m.getUser().getId(), m.getUser().getName());
                }
                System.out.print("\rUsers Processed: " + count);
                count++;
                userFound = false;
            }
            System.out.println("\nUsers Loaded - "+ Data.userList.size());
            e.getChannel().sendMessage("> Users loaded.").queue();
        }
        else{
            e.getChannel().sendMessage("> You do not have permission for this command").queue();
        }
    }
}