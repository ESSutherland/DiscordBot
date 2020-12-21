package commands;

import data.CommandEmbed;
import data.Data;
import data.Points;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class GambleCommand {

    /*public static void command(GuildMessageReceivedEvent e, String[] message){
        if(message.length < 2){
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): `" + Data.PREFIX + "gamble {amount}`");
        }
        else{
            try {
                int points = Integer.parseInt(message[1]);

                Random random = new Random();
                int roll = random.nextInt(100);

                System.out.println("Roll: " + roll);

                if(roll % 2 == 0){
                    Points.addPoints(e, points);
                    winEmbed(e, points);
                }
                else{
                    Points.addPoints(e, -points);
                    loseEmbed(e, points);
                }

            }
            catch (Exception ex){
                CommandEmbed.errorEB(e,"Please use a whole number.");
            }
        }
    }

    private static void winEmbed(GuildMessageReceivedEvent e, int points){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.setTitle("Congrats!");
        eb.setDescription("You won and earned `" + points + "` points!");
        eb.addField("Current Points: ", Data.getUserPoints(e.getMember().getId()) + "", false);
        eb.setFooter(Data.authorFooter);

        e.getChannel().sendMessage(eb.build()).queue();
    }

    private static void loseEmbed(GuildMessageReceivedEvent e, int points){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.setTitle("Sorry!");
        eb.setDescription("You went in and lost `" + points + "` points!");
        eb.addField("Current Points: ", Data.getUserPoints(e.getMember().getId()) + "", false);
        eb.setFooter(Data.authorFooter);

        e.getChannel().sendMessage(eb.build()).queue();
    }*/
}
