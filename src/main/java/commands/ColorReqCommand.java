package commands;

import data.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ColorReqCommand extends ListenerAdapter {


    public static void command(GuildMessageReceivedEvent e, String message[]){

        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getName();
        String roleId;
        String colorHex;

        if(!Data.findUserInDB(userId)){
            Data.addUserToDB(userId, userName);
        }
        if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("colorChangeRoleId")))){
            e.getChannel().sendMessage("`You must be a Nitro Booster to change your color.`").queue();
        }
        else if(!e.getMessage().getChannel().getId().equals(Data.prop.getProperty("nitroChannelId"))){}
        else{
            //Check if message is long enough
            if(message.length < 2){
                e.getChannel().sendMessage("`Please include the Hex Color Code`").queue();
            }
            else{
                colorHex = message[1];
                //Create new entries for the Role
                try{
                    //Convert hex to color
                    String hex = ("#" + colorHex);
                    Color color = Color.decode(hex);

                    if(Data.roleID.length() > 1){
                        roleId = Data.roleID;
                        e.getGuild().getRoleById(roleId).getManager().setName(userName).setColor(color).queue();
                        Data.updateUserColorInDB(userId, userName, roleId, colorHex);

                        e.getChannel().sendMessage("`Updated color for user: " + userName + "`").queue();
                        System.out.println("Updated Role: " + roleId + " for User: " + userName + " (" + userId + ") - " + color.toString());
                    }
                    else{
                        Role r = e.getGuild().createRole().setName(userName).setColor(color).complete();
                        roleId = r.getId();
                        e.getGuild().modifyRolePositions(false).selectPosition(r).moveTo(Integer.parseInt(Data.prop.getProperty("rolePosition"))).queue();
                        e.getGuild().addRoleToMember(e.getMember(), r).queue();
                        Data.updateUserColorInDB(userId, userName, roleId, colorHex);
                        e.getChannel().sendMessage("`Created color for user: " + userName + "`").queue();
                        System.out.println("Created Role: " + roleId + " for User: " + userName + " (" + userId + ") - " + color.toString());
                    }
                }
                catch (NumberFormatException n){
                    System.out.println(n);
                    e.getChannel().sendMessage("`Please enter a valid Hex color code.`").queue();
                }
            }
        }
    }
}

