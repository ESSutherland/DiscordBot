package commands;

import commands.*;
import data.CommandData;
import data.Data;
import data.Modules;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;

public class AllCommands {

    public static boolean runCommand(GuildMessageReceivedEvent e){

        boolean command = false;

        String[] message = e.getMessage().getContentRaw().split( " ");
        String channel = e.getMessage().getChannel().getId();
        String first = message[0];

        UpdateCommand.command(e, message);

        try {
            if(Modules.isModuleEnabled("colors")){
                if((first.equalsIgnoreCase(Data.PREFIX + "color") || first.equalsIgnoreCase(Data.PREFIX + "colour")) && e.getMessage().getChannel().getId().equals(Data.prop.getProperty("nitroChannelId"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Color Command");
                    ColorCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("levels")){
                if(first.equalsIgnoreCase(Data.PREFIX + "level") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                    System.out.println(e.getAuthor().getName() + "- Executing Level Command");
                    LevelCommand.command(e,message);
                    command = true;
                }
                else if(first.equalsIgnoreCase(Data.PREFIX + "leveltop")&& (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                    System.out.println(e.getAuthor().getName() + "- Executing Level Top Command");
                    LevelTopCommand.command(e, message);
                    command = true;
                }
                else if(first.equalsIgnoreCase(Data.PREFIX + "addexp")&& (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                    System.out.println(e.getAuthor().getName() + "- Executing Add Exp Command");
                    AddExpCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("minecraft") && (channel.equalsIgnoreCase(Data.prop.getProperty("minecraftChannelId")) || channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel")))){
                if(first.equalsIgnoreCase(Data.PREFIX + "whitelist")){
                    MCWhitelistCommand.command(e, message);
                    command = true;
                }
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "loadusers") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                System.out.println(e.getAuthor().getName() + "- Executing Load Users Command");
                LoadUsersCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "module") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                System.out.println(e.getAuthor().getName() + "- Executing Module Command");
                ModuleCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "modules") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                System.out.println(e.getAuthor().getName() + "- Executing Modules Command");
                ModulesCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "command") && e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))){
                System.out.println(e.getAuthor().getName() + "- Executing Command Command");
                CommandCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "commands")){
                System.out.println(e.getAuthor().getName() + "- Executing Commands Command");
                CommandsCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "delete") && e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))){
                System.out.println(e.getAuthor().getName() + "- Executing Delete Command");
                DeleteCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "stop") && channel.equalsIgnoreCase(Data.prop.getProperty("adminChannel"))){
                System.out.println(e.getAuthor().getName() + "- Executing Stop Command");
                StopCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "purge")){
                System.out.println(e.getAuthor().getName() + "- Executing Purge Command");
                PurgeCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "bot") && channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId"))){
                System.out.println(e.getAuthor().getName() + "- Executing Bot Command");
                BotCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "info")){
                System.out.println(e.getAuthor().getName() + "- Executing Info Command");
                InfoCommand.command(e, message);
                command = true;
            }

            if(CommandData.isCommand(first) && !e.getAuthor().isBot()){
                System.out.println(e.getAuthor().getName() + "- Executing Custom Command: " + first);
                String commandMessage = CommandData.getMessage(first);
                String userParam = "{user}";
                if(commandMessage.contains(userParam)){
                    commandMessage = commandMessage.replace(userParam, e.getAuthor().getAsMention());
                }
                e.getMessage().getChannel().sendMessage(commandMessage).queue();
                command = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return command;
    }
}
