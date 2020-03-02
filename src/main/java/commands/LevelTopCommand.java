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
        eb.setColor(Color.CYAN);
        eb.setTitle("Top 5 Levels");
        int count = 1;
        try{
            String userRank = "";
            while(rs.next()){
                switch (count){
                    case 1: userRank = "**" + (count) + " - " + rs.getString("userName") + "**" + first;
                        break;
                    case 2: userRank = "**" + (count) + " - " + rs.getString("userName") + "**" + second;
                        break;
                    case 3: userRank = "**" + (count) + " - " + rs.getString("userName") + "**" + third;
                        break;
                    default: userRank = "**" + (count) + " - " + rs.getString("userName") + "**";
                }
                eb.addField(userRank, "> Level: " + rs.getInt("userLevel") + " - Exp: " + rs.getDouble("userExp") + "/" + Data.prop.getProperty("levelExp"), false);
                count++;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
