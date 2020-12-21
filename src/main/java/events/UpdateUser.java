package events;

import data.DBUser;
import data.Data;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UpdateUser {

    public static void update(GuildMessageReceivedEvent e, String[] message){
        String userId = e.getMember().getId();
        String userName = e.getMember().getUser().getName();

        if(Data.findUserInDB(userId)){
            DBUser user = Data.getDBUser(userId);
            if(user.getUserId().equalsIgnoreCase(userId) && !user.getUserName().equalsIgnoreCase(userName)){
                Data.updateUserInDB(userId, userName);
                System.out.println("Updating name for: " + user.getUserName() + " -> " + userName);
                if(user.getRoleId() != null){
                    e.getGuild().getRoleById(user.getRoleId()).getManager().setName(userName).queue();
                }
            }
        }
    }
}
