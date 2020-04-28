package data;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Experience {

    public static void addExp(GuildMessageReceivedEvent e, double numExp, Member member, boolean useMultiplier){

        double multiplier;
        String userId = member.getId();

        if(member.getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId"))) && useMultiplier){
            multiplier = Double.parseDouble(Data.prop.getProperty("nitroExp"));
        }
        else if(member.getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId"))) && useMultiplier){
            multiplier = Double.parseDouble(Data.prop.getProperty("subExp"));
        }
        else{
            multiplier = Double.parseDouble(Data.prop.getProperty("defaultExp"));
        }

        double expToAdd = numExp * multiplier;
        double totalExp = (Data.getUserExp(userId) + expToAdd);
        double levelExp = Double.parseDouble(Data.prop.getProperty("levelExp"));
        if(totalExp >= levelExp){
            while(totalExp >= levelExp){
                totalExp -= levelExp;
                Data.setUserLevel(userId, Data.getUserLevel(userId) + 1);
                buildEmbed(e, member);
                Data.setUserExp(userId, totalExp);
            }
        }
        else if(totalExp < 0){
            while(totalExp < 0){
                if(Data.getUserLevel(userId) == 1){
                    totalExp += levelExp;
                    if(totalExp > 0){
                        Data.setUserExp(userId, 0);
                    }
                }
                else{
                    totalExp += levelExp;
                    Data.setUserLevel(userId, Data.getUserLevel(userId) - 1);
                    buildEmbed(e, member);
                    Data.setUserExp(userId, totalExp);
                }
            }
        }
        else{
            Data.addExpToUser(userId, expToAdd);
        }
    }

    public static void addExp(GuildMessageReceivedEvent e, double numExp, boolean useMultiplier){
        addExp(e, numExp, e.getMember(), useMultiplier);
    }

    private static void buildEmbed(GuildMessageReceivedEvent e, Member m){
        String userId = m.getId();
        String userName = m.getAsMention();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Level Up!");
        eb.setColor(Color.CYAN);
        eb.setDescription("Congratulations, " + userName + " for reaching level " + Data.getUserLevel(userId) + "!");
        eb.setFooter("Bot by SpiderPigEthan");
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
