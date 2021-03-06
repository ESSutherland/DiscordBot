package commands;

import data.CommandData;
import data.Data;
import data.Modules;
import events.UpdateUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.sql.SQLException;

public class AllCommands {

    public static boolean runCommand(GuildMessageReceivedEvent e){

        boolean command = false;

        String[] message = e.getMessage().getContentRaw().split( " ");
        String channel = e.getMessage().getChannel().getId();
        String first = message[0].toLowerCase();

        UpdateUser.update(e, message);

        try {
            if(Modules.isModuleEnabled("colors")){
                if((first.equalsIgnoreCase(Data.PREFIX + "color") || first.equalsIgnoreCase(Data.PREFIX + "colour")) && e.getMessage().getChannel().getId().equals(Data.prop.getProperty("nitroChannelId"))){
                    System.out.println(e.getAuthor().getName() + "- Executing Color Command");
                    ColorCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("levels")){
                if(first.equalsIgnoreCase(Data.PREFIX + "level") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                    System.out.println(e.getAuthor().getName() + "- Executing Level Command");
                    LevelCommand.command(e,message);
                    command = true;
                }
                else if(first.equalsIgnoreCase(Data.PREFIX + "leveltop")&& (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                    System.out.println(e.getAuthor().getName() + "- Executing Level Top Command");
                    LevelTopCommand.command(e, message);
                    command = true;
                }
                else if(first.equalsIgnoreCase(Data.PREFIX + "addexp")&& (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                    System.out.println(e.getAuthor().getName() + "- Executing Add Exp Command");
                    AddExpCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("minecraft") && (channel.equalsIgnoreCase(Data.prop.getProperty("minecraftChannelId")) || e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                if(first.equalsIgnoreCase(Data.PREFIX + "whitelist")){
                    MCWhitelistCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("custom_commands")){
                if(first.equalsIgnoreCase(Data.PREFIX + "command") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                    System.out.println(e.getAuthor().getName() + "- Executing Command Command");
                    CommandCommand.command(e, message);
                    command = true;
                }

                if(first.equalsIgnoreCase(Data.PREFIX + "commands")){
                    System.out.println(e.getAuthor().getName() + "- Executing Commands Command");
                    CommandsCommand.command(e, message);
                    command = true;
                }

                if(first.equalsIgnoreCase(Data.PREFIX + "delete") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                    System.out.println(e.getAuthor().getName() + "- Executing Delete Command");
                    DeleteCommand.command(e, message);
                    command = true;
                }

                if(first.equalsIgnoreCase(Data.PREFIX + "update") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                    System.out.println(e.getAuthor().getName() + "- Executing Update Command");
                    UpdateCommand.command(e, message);
                    command = true;
                }

                if(CommandData.isCommand(first) && !e.getAuthor().isBot()){
                    System.out.println(e.getAuthor().getName() + "- Executing Custom Command: " + first);
                    String commandMessage = CommandData.getMessage(first);
                    String userParam = "{user}";
                    if(commandMessage.contains(userParam)){
                        commandMessage = commandMessage.replace(userParam, e.getAuthor().getAsMention());
                    }

                    switch (CommandData.getCommandUseLevel(first)){
                        case "-a": e.getMessage().getChannel().sendMessage(commandMessage).queue();
                            break;
                        case "-b": if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId"))) || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
                            e.getMessage().getChannel().sendMessage(commandMessage).queue();
                        }
                            break;
                        case "-s": if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId"))) || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)) {
                            e.getMessage().getChannel().sendMessage(commandMessage).queue();
                        }
                        case "-m": if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId"))) || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)) {
                            e.getMessage().getChannel().sendMessage(commandMessage).queue();
                        }
                            break;
                        default: if(e.getMember().getId().equalsIgnoreCase(CommandData.getCommandUseLevel(first)) || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
                            e.getMessage().getChannel().sendMessage(commandMessage).queue();
                        }
                    }
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("animal_crossing")){
                if(first.equalsIgnoreCase(Data.PREFIX + "villager")){
                    System.out.println(e.getAuthor().getName() + "- Executing Villager Command");
                    VillagerCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("agree") && channel.equalsIgnoreCase(Data.prop.getProperty("agreeChannelId"))){
                if(first.equalsIgnoreCase(Data.PREFIX + "agree")){
                    System.out.println(e.getAuthor().getName() + "- Executing Agree Command");
                    AgreeCommand.command(e, message);
                    command = true;
                }
            }

            if(Modules.isModuleEnabled("movie_night")){
                if(first.equalsIgnoreCase(Data.PREFIX + "movienight") && PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
                    System.out.println(e.getAuthor().getName() + "- Executing Movie Night Command");
                    MovieNightCommand.command(e, message);
                    command = true;
                }
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "game") && PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
                System.out.println(e.getAuthor().getName() + "- Executing Game Command");
                GameCommand.command(e, message);
                command = true;
            }

            /*if(Modules.isModuleEnabled("points")){
                if(first.equalsIgnoreCase(Data.PREFIX + "points")){
                    System.out.println(e.getAuthor().getName() + "- Executing Points Command");
                    PointsCommand.command(e, message);
                    command = true;
                }

                if(first.equalsIgnoreCase(Data.PREFIX + "gamble")){
                    System.out.println(e.getAuthor().getName() + "- Executing Gamble Command");
                    GambleCommand.command(e, message);
                    command = true;
                }

                if(first.equalsIgnoreCase(Data.PREFIX + "pointstop")){
                    System.out.println(e.getAuthor().getName() + "- Executing Points Top Command");
                    PointsTopCommand.command(e, message);
                    command = true;
                }

                if(first.equalsIgnoreCase(Data.PREFIX + "addpoints") && PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
                    System.out.println(e.getAuthor().getName() + "- Executing Add Points Command");
                    AddPointsCommand.command(e, message);
                    command = true;
                }
            }*/

            if(first.equalsIgnoreCase(Data.PREFIX + "loadusers") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                System.out.println(e.getAuthor().getName() + "- Executing Load Users Command");
                LoadUsersCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "module") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                System.out.println(e.getAuthor().getName() + "- Executing Module Command");
                ModuleCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "modules") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                System.out.println(e.getAuthor().getName() + "- Executing Modules Command");
                ModulesCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "stop") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                System.out.println(e.getAuthor().getName() + "- Executing Stop Command");
                StopCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "purge") && (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR))){
                System.out.println(e.getAuthor().getName() + "- Executing Purge Command");
                PurgeCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "bot") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)))){
                System.out.println(e.getAuthor().getName() + "- Executing Bot Command");
                BotCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "whois") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)))){
                System.out.println(e.getAuthor().getName() + "- Executing Info Command");
                InfoCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "help") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)))){
                System.out.println(e.getAuthor().getName() + "- Executing Help Command");
                HelpCommand.command(e, message);
                command = true;
            }

            if(first.equalsIgnoreCase(Data.PREFIX + "lookup") && (channel.equalsIgnoreCase(Data.prop.getProperty("botChannelId")) || (e.getMessage().getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
                    || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)))){
                System.out.println(e.getAuthor().getName() + "- Executing Lookup Command");
                LookupCommand.command(e, message);
                command = true;
            }

            /*if(first.equalsIgnoreCase(Data.PREFIX + "wiki")){
                System.out.println(e.getAuthor().getName() + "- Executing Wiki Command");
                WikiCommand.command(e, message);
                command = true;
            }*/
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return command;
    }
}
