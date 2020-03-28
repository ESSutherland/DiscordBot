package commands;

import data.Data;
import data.Experience;
import data.Modules;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        if(!e.getChannel().getId().equalsIgnoreCase(Data.prop.getProperty("joinChannelId"))){
            boolean command = false;

            String[] message = e.getMessage().getContentRaw().split( " ");
            String channel = e.getMessage().getChannel().getId();

            UpdateCommand.command(e, message);

            try {
                if(Modules.isModuleEnabled("colors")){
                    if((message[0].equalsIgnoreCase(Data.PREFIX + "color") || message[0].equalsIgnoreCase(Data.PREFIX + "colour")) && e.getMessage().getChannel().getId().equals(Data.prop.getProperty("nitroChannelId"))){
                        System.out.println(e.getAuthor().getName() + "- Executing Color Command");
                        ColorCommand.command(e, message);
                        command = true;
                    }
                }

                if(Modules.isModuleEnabled("levels")){
                    if(message[0].equalsIgnoreCase(Data.PREFIX + "level") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                        System.out.println(e.getAuthor().getName() + "- Executing Level Command");
                        LevelCommand.command(e,message);
                        command = true;
                    }
                    else if(message[0].equalsIgnoreCase(Data.PREFIX + "leveltop")&& (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                        System.out.println(e.getAuthor().getName() + "- Executing Level Top Command");
                        LevelTopCommand.command(e, message);
                        command = true;
                    }
                    else if(message[0].equalsIgnoreCase(Data.PREFIX + "addexp")&& (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                        System.out.println(e.getAuthor().getName() + "- Executing Add Exp Command");
                        AddExpCommand.command(e, message);
                        command = true;
                    }
                }

                if(Modules.isModuleEnabled("minecraft") && (channel.equalsIgnoreCase(Data.prop.getProperty("minecraftChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                    if(message[0].equalsIgnoreCase(Data.PREFIX + "whitelist")){
                        MCWhitelistCommand.command(e, message);
                        command = true;
                    }
                }

                if(message[0].equalsIgnoreCase(Data.PREFIX + "loadusers") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Load Users Command");
                    LoadUsersCommand.command(e, message);
                    command = true;
                }

                if(message[0].equalsIgnoreCase(Data.PREFIX + "module") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Module Command");
                    ModuleCommand.command(e, message);
                    command = true;
                }

                if(message[0].equalsIgnoreCase(Data.PREFIX + "modules") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Modules Command");
                    ListModulesCommand.command(e, message);
                    command = true;
                }

                if(message[0].equalsIgnoreCase(Data.PREFIX + "stop") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Stop Command");
                    StopCommand.command(e, message);
                    command = true;
                }

                if(message[0].equalsIgnoreCase(Data.PREFIX + "bot") && channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Bot Command");
                    BotCommand.command(e, message);
                    command = true;
                }

                if(!e.getAuthor().isBot() && !command && Modules.isModuleEnabled("levels")) {
                   Experience.addExp(e, 1, true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
