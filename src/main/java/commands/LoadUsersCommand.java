package commands;

import data.CommandEmbed;
import data.DBUser;
import data.Data;
import data.Modules;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.List;

public class LoadUsersCommand {
    public static void command(GuildMessageReceivedEvent e, String[] message){
        if(PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
            Guild guild = e.getGuild();
            List<Member> members = guild.getMembers();
            System.out.println("Loading Users - " + Data.userList.size());
            CommandEmbed.successEB(e, "Loading users.");
            for(Member m : members){
                boolean userFound = false;
                for(DBUser user : Data.userList){
                    if(user.getUserId().equalsIgnoreCase(m.getUser().getId())){
                        userFound = true;
                    }
                }
                if(!userFound){
                    System.out.println("Adding " + m.getUser().getId() + " - " + m.getUser().getName() + " to DB.");
                    Data.addUserToDB(m.getUser().getId(), m.getUser().getName());
                }
                if(!m.getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("userRoleId"))) && !m.getUser().isBot()){
                    try {
                        if (Modules.isModuleEnabled("agree") && !m.getRoles().contains(guild.getRoleById(Data.prop.getProperty("defaultRoleId"))))
                            e.getGuild().addRoleToMember(m, guild.getRoleById(Data.prop.getProperty("defaultRoleId"))).queue();
                        else if (!Modules.isModuleEnabled("agree"))
                            e.getGuild().addRoleToMember(m, guild.getRoleById(Data.prop.getProperty("userRoleId"))).queue();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            for(DBUser user : Data.userList){
                if(!e.getGuild().getMembers().contains(guild.getMemberById(user.getUserId()))){
                    System.out.println("Removing " + user.getUserName() + " from DB");
                    Data.removeUserFromDB(user.getUserId());
                }

                if(user.getRoleId() != null && !guild.getMemberById(user.getUserId()).getRoles().contains(guild.getRoleById(Data.prop.getProperty("nitroRoleId")))){
                    guild.getRoleById(user.getRoleId()).delete().queue();
                    System.out.println("Deleting Nitro Role For " + user.getUserName());
                    Data.updateUserColorInDB(user.getUserId(), user.getUserName(), null, null);
                }
            }
            System.out.println("\nUsers Loaded - "+ Data.userList.size());
            CommandEmbed.successEB(e, "Users loaded.");
        }
        else{
            CommandEmbed.errorEB(e, "You do not have permission for this command");
        }
    }

    public static void startup(JDA jda){
        Guild guild = jda.getGuilds().get(0);
        List<Member> members = jda.getGuilds().get(0).getMembers();
        System.out.println("Loading Users - " + Data.userList.size());
        for(Member m : members){
            boolean userFound = false;
            for(DBUser user : Data.userList){
                if(user.getUserId().equalsIgnoreCase(m.getUser().getId())){
                    userFound = true;
                }
            }
            if(!userFound){
                System.out.println("Adding " + m.getUser().getId() + " - " + m.getUser().getName() + " to DB.");
                Data.addUserToDB(m.getUser().getId(), m.getUser().getName());
            }

            if(!m.getRoles().contains(guild.getRoleById(Data.prop.getProperty("userRoleId"))) && !m.getUser().isBot()){
                try {
                    if (Modules.isModuleEnabled("agree") && !m.getRoles().contains(guild.getRoleById(Data.prop.getProperty("defaultRoleId"))))
                        guild.addRoleToMember(m, guild.getRoleById(Data.prop.getProperty("defaultRoleId"))).queue();
                    else if (!Modules.isModuleEnabled("agree"))
                        guild.addRoleToMember(m, guild.getRoleById(Data.prop.getProperty("userRoleId"))).queue();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        for(DBUser user : Data.userList){
            if(!members.contains(guild.getMemberById(user.getUserId()))) {
                System.out.println("Removing " + user.getUserName() + " from DB");

                if(user.getRoleId() != null){
                    guild.getRoleById(user.getRoleId()).delete().queue();
                    System.out.println("Deleting Nitro Role For " + user.getUserName());
                    Data.updateUserColorInDB(user.getUserId(), user.getUserName(), null, null);
                }

                if (user.getMcUsername() != null) {
                    MCWhitelistCommand.connect();
                    MCWhitelistCommand.unWhitelist(user.getUserId());
                    MCWhitelistCommand.disconnect();
                }
                Data.removeUserFromDB(user.getUserId());
            }
            else{
                if(user.getRoleId() != null && !guild.getMemberById(user.getUserId()).getRoles().contains(guild.getRoleById(Data.prop.getProperty("nitroRoleId")))){
                    guild.getRoleById(user.getRoleId()).delete().queue();
                    System.out.println("Deleting Nitro Role For " + user.getUserName());
                    Data.updateUserColorInDB(user.getUserId(), user.getUserName(), null, null);
                }

                if(user.getMcUsername() != null && !guild.getMemberById(user.getUserId()).getRoles().contains(guild.getRoleById(Data.prop.getProperty("subRoleId")))){
                    MCWhitelistCommand.connect();
                    MCWhitelistCommand.unWhitelist(user.getUserId());
                    MCWhitelistCommand.disconnect();
                    Data.updateMCUserName(null, user.getUserId());
                }
            }
        }
        System.out.println("\nUsers Loaded - "+ Data.userList.size());
    }
}
