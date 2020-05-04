package commands;

import data.CommandData;
import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandsCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {
        ArrayList<String[]> commandList = CommandData.getCommands();

        if(commandList.size() > 0){
            String list = "```\n";
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Data.botColor);
            eb.setTitle("Custom Commands");

            for(String[] s: commandList){
                eb.addField("" , s[0], false);
            }
            eb.setFooter("Bot by SpiderPigEthan");
            e.getChannel().sendMessage(eb.build()).queue();

            /*for(String[] s: commandList){
                list += s[0] + ": " + s[1] + "\n\n";
            }
            list += "```";
            e.getChannel().sendMessage(list).queue();*/

        }
        else{
            CommandEmbed.errorEB(e, "There are no commands.");
        }
    }
}
