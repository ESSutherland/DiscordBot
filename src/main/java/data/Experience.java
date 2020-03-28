package data;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

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
                Data.levelUpUser(userId);
                String userName = member.getAsMention();
                e.getChannel().sendMessage("> Congratulations, " + userName + " for reaching level " + Data.getUserLevel(userId) + "!").queue();
                Data.setUserExp(userId, totalExp);
            }
        }
        else{
            Data.addExpToUser(userId, expToAdd);
        }
    }

    public static void addExp(GuildMessageReceivedEvent e, double numExp, boolean useMultiplier){
        addExp(e, numExp, e.getMember(), useMultiplier);
    }
}
