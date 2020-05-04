package commands;

import data.CommandEmbed;
import data.Data;
import data.Modules;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.sql.SQLException;

public class HelpCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {
        User user = e.getAuthor();

        String commands ="";

        commands += "__**General Commands**__\n" +
                "**" + Data.PREFIX + "bot**: " +
                "*Get information about the bot.*\n" +
                "**" + Data.PREFIX + "help**: " +
                "*Get information about commands.*\n" +
                "**" + Data.PREFIX + "whois**: " +
                "*Get information about the user.*\n\n";

        if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
            commands += "__**Moderator Commands**__\n" +
                    "**" + Data.PREFIX + "purge {amount} [@user]** - ({}=required, []=optional): " +
                    "*Purge a specified number of message by the user or tagged user out of the last 100 total messages.*\n" +
                    "**" + Data.PREFIX + "stop**: " +
                    "*Shut down the bot (Please try not to use this).*\n" +
                    "**" + Data.PREFIX + "module {name} {enable/disable}** - ({}=required): " +
                    "*Enable or disable feature modules in the bot.*\n" +
                    "**" + Data.PREFIX + "modules**: " +
                    "*List all modules and their status.*\n" +
                    "**" + Data.PREFIX + "loadusers**: " +
                    "*Manually update the database for the bot (Mostly in case of bot outages).*\n\n";
        }

        if(Modules.isModuleEnabled("colors")){
            commands += "__**Color Commands**__\n" +
                    "**" + Data.PREFIX + "color/colour {hex}** - ({}=required): " +
                    "*Allows Nitro Boosters to change the color of their name in the server.*\n\n";
        }

        if(Modules.isModuleEnabled("levels")){
            commands += "__**Level Commands**__\n" +
                    "**" + Data.PREFIX + "level [@user]** - ([]=optional): " +
                    "*Displays the current level of the user or mentioned user(s).*\n" +
                    "**" + Data.PREFIX + "leveltop**: " +
                    "*Displays the top 5 users with the highest level.*\n" +
                    "**" + Data.PREFIX + "addexp {amount} [@user]** - ({}=required, []=optional): " +
                    "*Adds the specified amount of experience to the user or mentioned user. (MODERATORS ONLY)*\n\n";
        }

        if(Modules.isModuleEnabled("minecraft")){
            commands += "__**Minecraft Commands**__\n" +
                    "**" + Data.PREFIX + "whitelist {username}** - ({}=required): " +
                    "*Allows users to whitelist themselves on the linked Minecraft server.*\n\n";
        }

        if(Modules.isModuleEnabled("custom_commands")){
            commands += "__**Custom Commands**__\n" +
                    "**" + Data.PREFIX + "commands**: " +
                    "*Shows list of the custom commands on the server*\n" +
                    "**" + Data.PREFIX + "command {command} {response}** - ({}=required): " +
                    "*Create a custom command to use in the server, use '{user}' in the response to ping the user. (MODERATORS ONLY)*\n" +
                    "**" + Data.PREFIX + "delete {command}** - ({}=required): " +
                    "*Delete a custom command from the server. (MODERATORS ONLY)*\n\n";
        }

        if(Modules.isModuleEnabled("animal_crossing")){
            commands += "__**Animal Crossing Commands**__\n" +
                    "**" + Data.PREFIX + "villager {name}** - ({}=required): " +
                    "*Get information about the specified Animal Crossing: New Horizons villager.*\n\n";
        }

        try{
            user.openPrivateChannel().complete().sendMessage(commands).complete();
        }
        catch (Exception ex){
            CommandEmbed.errorEB(e, "Unable to send direct message to user. This may be due to your privacy settings.");
        }
    }
}
