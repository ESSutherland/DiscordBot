package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class LevelCommand {

    public static void command(GuildMessageReceivedEvent e, String message[]){
        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getName();
        int level = Data.getUserLevel(userId);
        int exp = Data.getUserExp(userId);
        String levelExp = Data.prop.getProperty("levelExp");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setAuthor(userName, null, e.getMember().getUser().getAvatarUrl());
        eb.addField("Level", "" + Data.getUserLevel(userId), true);
        eb.addBlankField(true);
        eb.setThumbnail("https://cdn.discordapp.com/attachments/673722670294237187/675895981560168484/UwU_112.png");
        eb.addField("Exp to level " + (level + 1), exp + "/" + levelExp, true);
        eb.setFooter("ID: " + userId);

        //e.getChannel().sendMessage("> " + userName + " is level " + level + ". (" + exp + "/" + levelExp + " from level " + (level + 1) + ").").queue();
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
