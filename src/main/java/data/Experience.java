package data;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Experience {

    public static void addExp(GuildMessageReceivedEvent e, double numExp, String userId, boolean useMultiplier){
        String userName = e.getGuild().getMemberById(userId).getAsMention();

        double multiplier;
        if(e.getGuild().getMemberById(userId).getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId"))) && useMultiplier){
            multiplier = Double.parseDouble(Data.prop.getProperty("nitroExp"));
        }
        else if(e.getGuild().getMemberById(userId).getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId"))) && useMultiplier){
            multiplier = Double.parseDouble(Data.prop.getProperty("subExp"));
        }
        else{
            multiplier = Double.parseDouble(Data.prop.getProperty("defaultExp"));
        }

        double expToAdd = numExp * multiplier;
        double totalExp = (Data.getUserExp(userId) + expToAdd);
        if(totalExp >= Double.parseDouble(Data.prop.getProperty("levelExp"))){
            while(totalExp >= Double.parseDouble(Data.prop.getProperty("levelExp"))){
                totalExp -= Double.parseDouble(Data.prop.getProperty("levelExp"));
                Data.levelUpUser(userId);
                e.getChannel().sendMessage("> Congratulations, " + userName + " for reaching level " + Data.getUserLevel(userId) + "!").queue();
                Data.setUserExp(userId, totalExp);
            }
        }
        else{
            Data.addExpToUser(userId, expToAdd);
        }
    }

    public static void addExp(GuildMessageReceivedEvent e, double numExp, boolean useMultiplier){
        String userId = e.getMember().getUser().getId();
        addExp(e, numExp, userId, useMultiplier);
    }
}
