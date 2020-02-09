package data;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Experience {

    public static void addExp(GuildMessageReceivedEvent e, int numExp){

        String userId = e.getMember().getUser().getId();
        String userName = e.getMember().getUser().getAsMention();

        int multiplier;
        if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            multiplier = 3;
        }
        else if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId")))){
            multiplier = 2;
        }
        else{
            multiplier = 1;
        }

        int expToAdd = numExp * multiplier;

        if(Data.getUserExp(userId) + expToAdd > Integer.parseInt(Data.prop.getProperty("levelExp"))){
            expToAdd = (Data.getUserExp(userId) + expToAdd) - Integer.parseInt(Data.prop.getProperty("levelExp"));
            Data.levelUpUser(userId);
            e.getChannel().sendMessage("> Congratulations, " + userName + " for reaching level " + Data.getUserLevel(userId) + "!").queue();
            Data.setUserExp(userId, expToAdd);
        }
        else{
            Data.addExpToUser(userId, expToAdd);
        }
    }
}
