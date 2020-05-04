package events;

import commands.AllCommands;
import data.CommandData;
import data.Data;
import data.Experience;
import data.Modules;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.sql.SQLException;

public class Message extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        if(e.getMessage().getType().equals(MessageType.DEFAULT)){

            boolean command = AllCommands.runCommand(e);

            if(e.getChannel().getId().equalsIgnoreCase(Data.prop.getProperty("agreeChannelId")) && !(e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
            || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                e.getMessage().delete().queue();
            }

            try {
                if(!e.getAuthor().isBot() && !command && Modules.isModuleEnabled("levels")) {
                    Experience.addExp(e, 1, true);
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else {
            try {
                if ((e.getMessage().getType().equals(MessageType.GUILD_MEMBER_BOOST) ||
                        e.getMessage().getType().equals(MessageType.GUILD_BOOST_TIER_1) ||
                        e.getMessage().getType().equals(MessageType.GUILD_BOOST_TIER_2) ||
                        e.getMessage().getType().equals(MessageType.GUILD_BOOST_TIER_3)) && Modules.isModuleEnabled("boost_message")){
                    System.out.println("Boost Message");
                    String boostMessage = Data.prop.getProperty("boostMessage");
                    String userParam = "{user}";
                    if(boostMessage.contains(userParam)){
                        boostMessage = boostMessage.replace(userParam, e.getAuthor().getAsMention());
                    }

                    e.getGuild().getTextChannelById(Data.prop.getProperty("generalChannelId"))
                            .sendMessage(boostMessage).queue();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
