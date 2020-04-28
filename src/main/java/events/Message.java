package events;

import commands.AllCommands;
import data.CommandData;
import data.Data;
import data.Experience;
import data.Modules;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class Message extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        if(e.getMessage().getType().equals(MessageType.DEFAULT)){

            boolean command = AllCommands.runCommand(e);

            try {
                if(!e.getAuthor().isBot() && !command && Modules.isModuleEnabled("levels")) {
                    Experience.addExp(e, 1, true);
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getMessage().getType().equals(MessageType.GUILD_MEMBER_BOOST)){
            System.out.println("Boost Message");
            String boostMessage = Data.prop.getProperty("boostMessage");
            String userParam = "{user}";
            if(boostMessage.contains(userParam)){
                boostMessage = boostMessage.replace(userParam, e.getAuthor().getAsMention());
            }

            e.getGuild().getTextChannelById(Data.prop.getProperty("generalChannelId"))
                    .sendMessage(boostMessage).queue();
        }
    }
}
