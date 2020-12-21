package commands;

import data.CommandEmbed;
import data.Data;
import data.Points;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AddPointsCommand {
    /*public static void command(GuildMessageReceivedEvent e, String[] message){

        int numPoints;
        String userId;
        if(message.length > 2){
            numPoints = Integer.parseInt(message[1]);
            Member m = e.getMessage().getMentionedMembers().get(0);
            userId = m.getId();
            CommandEmbed.successEB(e,"Added `" + numPoints + "` points to " + e.getGuild().getMemberById(userId).getAsMention() + ".");
            Points.addPoints(e,numPoints, m);
        }
        else if(message.length > 1){
            numPoints = Integer.parseInt(message[1]);
            CommandEmbed.successEB(e,"Added `" + numPoints + "` points to " + e.getMember().getAsMention() + ".");
            Points.addPoints(e, numPoints);
        }
        else{
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required, []=optional): `" + Data.PREFIX + "addpoints {amount} [user]`");
        }
    }*/
}
