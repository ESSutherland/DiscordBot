package data;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class CommandEmbed {

    public static void successEB(GuildMessageReceivedEvent e, String text){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.addField("Success!", text, true);
        eb.addBlankField(true);
        eb.setFooter("Bot by SpiderPigEthan");

        e.getChannel().sendMessage(eb.build()).queue();
    }
    public static void successEB(GuildMessageReceivedEvent e, String text, String hex){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.decode(hex));
        eb.setThumbnail("https://htmlcolors.com/color-image/" + hex.substring(1) + ".png");
        eb.addField("Success!", text, true);
        eb.addBlankField(true);
        eb.setFooter("Bot by SpiderPigEthan");

        e.getChannel().sendMessage(eb.build()).queue();
    }
    public static void errorEB(GuildMessageReceivedEvent e, String text){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.addField("Error!", text, true);
        eb.addBlankField(true);
        eb.setFooter("Bot by SpiderPigEthan");

        e.getChannel().sendMessage(eb.build()).queue();
    }
}
