package data;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class CommandEmbed {

    public static void successEB(GuildMessageReceivedEvent e, String text){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.addField("Success!", text, true);
        eb.addBlankField(true);
        eb.setFooter("Bot by SpiderPigEthan");

        e.getChannel().sendMessage(eb.build()).queue();
    }
    public static void successEB(GuildMessageReceivedEvent e, String text, String hex){

        Color color = Color.decode(hex);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(color);

        String calcHex = String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());

        eb.setThumbnail("https://htmlcolors.com/color-image/" + calcHex + ".png");

        eb.addField("Success!", text + "#" + calcHex, true);
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
