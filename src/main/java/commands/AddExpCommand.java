package commands;

import data.CommandEmbed;
import data.Data;
import data.Experience;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.awt.*;

public class AddExpCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        int numExp;
        String userId;
        if(message.length > 2){
            numExp = Integer.parseInt(message[1]);
            Member m = e.getMessage().getMentionedMembers().get(0);
            userId = m.getId();
            CommandEmbed.successEB(e,"Added `" + numExp + "` experience to " + e.getGuild().getMemberById(userId).getAsMention() + ".");
            Experience.addExp(e,numExp, m, false);
        }
        else if(message.length > 1){
            numExp = Integer.parseInt(message[1]);
            CommandEmbed.successEB(e,"Added `" + numExp + "` experience to " + e.getMember().getAsMention() + ".");
            Experience.addExp(e, numExp, false);
        }
        else{
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required, []=optional): `" + Data.PREFIX + "addexp {amount} [user]`");
        }
    }
}
