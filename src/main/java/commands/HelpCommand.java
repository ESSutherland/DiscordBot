package commands;

import data.Data;
import data.Modules;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


import java.sql.SQLException;

public class HelpCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {

        User bot = e.getJDA().getSelfUser();

        String url = "https://essutherland.github.io/bot-site/?prefix=" + Data.PREFIX + "&bot_name=" + bot.getName();

        if(Modules.isModuleEnabled("colors"))
            url += "&color=1";
        if(Modules.isModuleEnabled("levels"))
            url += "&levels=1";
        if(Modules.isModuleEnabled("minecraft"))
            url += "&minecraft=1";
        if(Modules.isModuleEnabled("animal_crossing"))
            url += "&animalcrossing=1";
        if(Modules.isModuleEnabled("custom_commands"))
            url += "&custom=1";

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(bot.getName(), null, bot.getAvatarUrl());
        eb.setTitle("CLICK HERE FOR LIST OF COMMANDS", url);
        eb.setColor(Data.botColor);
        eb.setFooter(Data.authorFooter);

        e.getChannel().sendMessage(eb.build()).queue();
    }
}
