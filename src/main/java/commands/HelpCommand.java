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

        commands += "__General Commands__\n" +
                "**" + Data.PREFIX + "bot**: " +
                "*Get information about the bot.*\n" +
                "**" + Data.PREFIX + "help**: " +
                "*Get information about commands.*\n" +
                "**" + Data.PREFIX + "whois**: " +
                "*Get information about the user.*\n\n";

        if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))
        || PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
            commands += "__Moderator Commands__\n" +
                    "**" + Data.PREFIX + "purge {amount} [@user]** - ({}=required, []=optional): " +
                    "*Purge a specified number of message by the user or tagged user out of the last 100 messages.*\n" +
                    "**" + Data.PREFIX + "stop**: " +
                    "*Shut down the bot (Please try not to use this).*\n" +
                    "**" + Data.PREFIX + "module {name} {enable/disable}** - ({}=required): " +
                    "*Enable or disable feature modules in the bot.*\n" +
                    "**" + Data.PREFIX + "modules**: " +
                    "*List all modules and their status.*\n\n";
        }

        if(Modules.isModuleEnabled("colors")){
            commands += "__Color Commands__\n" +
                    "**" + Data.PREFIX + "color/colour {hex}** - ({}=required): " +
                    "*Allows Nitro Boosters to change the color of their name in the server.*\n\n";
        }

        if(Modules.isModuleEnabled("levels")){
            commands += "__Level Commands__\n" +
                    "**" + Data.PREFIX + "level [@user]** - ([]=optional): " +
                    "*Displays the current level of the user or mentioned user(s).*\n" +
                    "**" + Data.PREFIX + "leveltop**: " +
                    "*Displays the top 5 users with the highest level.*\n" +
                    "**" + Data.PREFIX + "addexp {amount} [@user]** - ({}=required, []=optional): " +
                    "*Adds the specified amount of experience to the user or mentioned user. (MOD ONLY)*\n\n";
        }

        if(Modules.isModuleEnabled("minecraft")){
            commands += "__Minecraft Commands__\n" +
                    "**" + Data.PREFIX + "whitelist {username}** - ({}=required): " +
                    "*Allows users to whitelist on the linked Minecraft server.*\n\n";
        }

        if(Modules.isModuleEnabled("custom_commands")){
            commands += "__Custom Commands__\n" +
                    "**" + Data.PREFIX + "commands**: " +
                    "*Shows list of the custom commands on the server*\n" +
                    "**" + Data.PREFIX + "command {permission flag} {command} {response}** - ({}=required): " +
                    "*Create a custom command to use in the server. flags: -a = everyone, -b = boosters, -s = subs, @ a user for a private command. (MOD ONLY)*\n" +
                    "**" + Data.PREFIX + "delete {command}** - ({}=required): " +
                    "*Delete a custom command from the server. (MOD ONLY)*\n\n";
        }

        if(Modules.isModuleEnabled("animal_crossing")){
            commands += "__Animal Crossing Commands__\n" +
                    "**" + Data.PREFIX + "villager {name}** - ({}=required): " +
                    "*Get information about the specified Animal Crossing: New Horizons villager.*\n\n";
        }

        commands += "__Other Commands__\n" +
                "**" + Data.PREFIX + "wiki {search parameters}** - ({}=required): " +
                "*Get an article from the NoPixel Wiki about the specified parameters (Based on search results, so might not return the expected result).*\n\n";

        try{
            user.openPrivateChannel().complete().sendMessage(commands).complete();
        }
        catch (Exception ex){
            CommandEmbed.errorEB(e, "Unable to send direct message to user. This may be due to your privacy settings.");
        }
    }
}
