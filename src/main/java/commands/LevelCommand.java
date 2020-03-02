package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class LevelCommand {

    public static void command(GuildMessageReceivedEvent e, String message[]){
        int level;
        double exp;
        int userRank;
        Member member;
        String userId;
        String userName;
        if (message.length > 1) {
            userId = message[1];
            userName = e.getGuild().getMemberById(userId).getUser().getName();
            member = e.getGuild().getMemberById(userId);
        }
        else{
            userId = e.getMember().getUser().getId();
            userName = e.getMember().getUser().getName();
            member = e.getMember();
        }
        level = Data.getUserLevel(userId);
        exp = Data.getUserExp(userId);
        userRank = Data.getUserRank(userId);
        String levelExp = Data.prop.getProperty("levelExp");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle("Rank: #" + userRank);
        eb.setAuthor(userName, null, member.getUser().getAvatarUrl());
        eb.addField("Level", "" + Data.getUserLevel(userId), true);
        eb.addBlankField(true);
        eb.setThumbnail("https://cdn.discordapp.com/attachments/673722670294237187/675895981560168484/UwU_112.png");
        eb.addField("Exp to level " + (level + 1), exp + "/" + levelExp, true);
        eb.setFooter("ID: " + userId);
        if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            eb.setDescription("Multiplier: " + e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")).getName() + "(x" + Data.prop.getProperty("nitroExp") + ")");
        }
        else if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId")))){
            eb.setDescription("Exp Multiplier: " + e.getGuild().getRoleById(Data.prop.getProperty("subRoleId")).getName() + "(x" + Data.prop.getProperty("subExp") + ")");
        }

        e.getChannel().sendMessage(eb.build()).queue();
    }
}
