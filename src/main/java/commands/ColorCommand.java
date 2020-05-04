package commands;

import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class ColorCommand {


    public static void command(GuildMessageReceivedEvent e, String[] message){

        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getName();
        String roleId;
        String colorHex;
        
        if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            CommandEmbed.errorEB(e, "You must be a Nitro Booster to change your color.");
        }
        else{
            if(message.length < 2){
                CommandEmbed.errorEB(e, "Please use correct parameters ({}=required) " + Data.PREFIX + "color/colour {hex}");
            }
            else{
                colorHex = message[1];
                //Create new entries for the Role
                try{
                    if(colorHex.charAt(0) == '#'){
                        colorHex = colorHex.substring(1); //#123456
                    }

                    //Convert hex to color
                    String hex = ("#" + colorHex);
                    Color color = Color.decode(hex);

                    if(Data.findUserInDB(userId)){
                        System.out.println("User Found");
                        roleId = Data.getDBUser(userId).getRoleId();
                        if(roleId != null){
                            System.out.println("Role Found");
                            e.getGuild().getRoleById(roleId).getManager().setName(userName).setColor(color).queue();
                            Data.updateUserColorInDB(userId, userName, roleId, colorHex);
                            CommandEmbed.successEB(e, "Updated color for " + e.getMember().getAsMention() + ": " + hex, hex);
                            System.out.println("Updated Role: " + roleId + " for User: " + userName + " (" + userId + ") - " + color.toString());
                        }
                        else{
                            System.out.println("No Role Found, Creating");
                            Role r = e.getGuild().createRole().setName(userName).setColor(color).complete();
                            roleId = r.getId();
                            e.getGuild().modifyRolePositions(false).selectPosition(r).moveTo(Integer.parseInt(Data.prop.getProperty("rolePosition"))).queue();
                            e.getGuild().addRoleToMember(e.getMember(), r).queue();
                            Data.updateUserColorInDB(userId, userName, roleId, colorHex);
                            CommandEmbed.successEB(e, "Created color for " + e.getMember().getAsMention() + ": " + hex,  hex);
                            System.out.println("Created Role: " + roleId + " for User: " + userName + " (" + userId + ") - " + color.toString());
                        }
                    }
                    else{
                        System.out.println("User Not Found, Adding To DB");
                        Data.addUserToDB(userId, userName);
                        command(e, message);
                    }
                }
                catch (NumberFormatException n){
                    System.out.println("Invalid Format");;
                    CommandEmbed.errorEB(e, "Please enter a valid Hex color code.");
                }
            }
        }
    }
}

