package commands;

import data.Modules;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListModulesCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {
        ArrayList<String[]> modulesList  = Modules.getModules();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle("Modules");

        for(String[] s: modulesList){
            String enabled = "";
            if(s[1].equalsIgnoreCase("0"))
                enabled = "disabled";
            else if(s[1].equalsIgnoreCase("1"))
                enabled = "enabled";

            eb.addField(s[0], enabled, true);
        }
        eb.setFooter("Bot by SpiderPigEthan");
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
