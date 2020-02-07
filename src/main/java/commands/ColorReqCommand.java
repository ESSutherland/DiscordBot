package commands;

import data.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ColorReqCommand extends ListenerAdapter {


    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        String[] message = e.getMessage().getContentRaw().split( " ");

        if(message[0].equalsIgnoreCase("!color") || message[0].equalsIgnoreCase("!colour")){
            //Ensure there are parameters

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
                    //Get name and id
                    String userId = e.getMember().getUser().getId();
                    String userName = e.getMember().getUser().getName();
                    String roleId = "";
                    String colorHex = message[1];

                    //Create new entries for the Role
                    try{
                        //Convert hex to color
                        String hex = ("#" + colorHex);
                        Color color = Color.decode(hex);

                        if(Data.findUserInDB(userId)){
                            e.getGuild().getRoleById(Data.roleID).getManager().setName(Data.userNAME).setColor(color).queue();
                            roleId = Data.roleID;
                            Data.updateUserDB(userId, userName, roleId, colorHex);
                            e.getChannel().sendMessage("`Updated role for user: " + Data.userNAME + "`").queue();
                            System.out.println("Updated Role: " + Data.roleID + " for User: " + Data.userNAME + " (" + Data.userID + ") - " + color.toString());
                        }
                        else{
                            Role r = e.getGuild().createRole().setName(userName).setColor(color).complete();
                            roleId = r.getId();
                            e.getGuild().modifyRolePositions(false).selectPosition(r).moveTo(Integer.parseInt(Data.prop.getProperty("rolePosition"))).queue();
                            e.getGuild().addRoleToMember(e.getMember(), r).queue();

                            Data.addUserToDB(userId, userName, roleId, colorHex);
                            e.getChannel().sendMessage("`Created role for user: " + Data.userNAME + "`").queue();
                            System.out.println("Created Role: " + Data.roleID + " for User: " + Data.userNAME + " (" + Data.userID + ") - " + color.toString());
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
}

