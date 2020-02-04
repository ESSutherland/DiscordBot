package commands;

import data.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ColorReqCommand extends ListenerAdapter {

    public ColorReqCommand(){
        Data.loadData();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        String[] message = e.getMessage().getContentRaw().split( " ");

        if(message[0].equalsIgnoreCase("!color") || message[0].equalsIgnoreCase("!colour")){
            //Ensure there are parameters

            if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("colorChangeRoleId")))){
                e.getChannel().sendMessage("`You must be a Nitro Booster to change your color.`").queue();
            }
            else if(!e.getMessage().getChannel().getId().equals(Data.prop.getProperty("nitroChannelId"))){

            }
            else{
                if(message.length < 2){
                    e.getChannel().sendMessage("`Please include the Hex Color Code`").queue();
                }
                else{
                    String userId = e.getMember().getUser().getId();
                    String userName = e.getMember().getUser().getName();
                    int index = -1;

                    //Check if user already has a Role saved
                    for(int i = 0; i < Data.userIds.size(); i++){
                        if(Data.userIds.get(i).equals(userId)) {
                            index = i;
                        }
                    }
                    //Create new entries for the Role
                    if(index < 0) {
                        Data.userIds.add(userId);
                        Data.userNames.add(userName);
                        index = Data.userIds.size() - 1;
                    }

                    try{
                        //Convert hex to color
                        String hex = ("#" + message[1]);
                        Color color = Color.decode(hex);

                        //Creates a new role if user doesn't have one
                        if (Data.roleIds.size() <= index){
                            Role r = e.getGuild().createRole().setName(Data.userNames.get(index)).setColor(color).complete();
                            Data.roleIds.add(r.getId());
                            e.getGuild().modifyRolePositions(false).selectPosition(r).moveTo(Integer.parseInt(Data.prop.getProperty("rolePosition"))).queue();
                            e.getGuild().addRoleToMember(e.getMember(), r).queue();

                            e.getChannel().sendMessage("`Created role for user: " + Data.userNames.get(index) + "`").queue();
                            System.out.println("Created Role: " + Data.roleIds.get(index) + " for User: " + Data.userNames.get(index) + " (" + Data.userIds.get(index) + ") - " + color.toString());
                        }
                        //Updates existing role
                        else {
                            e.getGuild().getRoleById(Data.roleIds.get(index)).getManager().setName(Data.userNames.get(index)).setColor(color).queue();

                            e.getChannel().sendMessage("`Updated role for user: " + Data.userNames.get(index) + "`").queue();
                            System.out.println("Updated Role: " + Data.roleIds.get(index) + " for User: " + Data.userNames.get(index) + " (" + Data.userIds.get(index) + ") - " + color.toString());
                        }
                        Data.writeData();
                    }
                    catch (NumberFormatException n){
                        e.getChannel().sendMessage("`Please enter a valid Hex color code.`").queue();
                    }
                }
            }
        }
    }
}

