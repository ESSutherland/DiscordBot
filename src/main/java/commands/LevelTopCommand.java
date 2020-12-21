package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.ResultSet;

public class LevelTopCommand {

    public static void command(GuildMessageReceivedEvent e, String message[]){
        ResultSet rs = Data.getTopLevels();

        String first = ":first_place:";
        String second = ":second_place:";
        String third = ":third_place:";

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.setTitle("Top 5 Levels in " + e.getGuild().getName());
        int count = 1;
        try{
            String userRank = "";
            while(rs.next()){
                switch (count){
                    case 1: userRank = "**" + first;
                        break;
                    case 2: userRank = "**" + second;
                        break;
                    case 3: userRank = "**" + third;
                        break;
                    default: userRank = "**-" + (count) + "-";
                }
                eb.addField("", userRank + " " + e.getGuild().getMemberById(rs.getString("userId")).getAsMention() + "**", true);
                eb.addField("", "`Level: " + rs.getInt("userLevel") + "`", true);
                eb.addField("",  "Exp: " + rs.getDouble("userExp") + "/" + Data.prop.getProperty("levelExp"), true);
                count++;
            }
            eb.setFooter(Data.authorFooter);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
