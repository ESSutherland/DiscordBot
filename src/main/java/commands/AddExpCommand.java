package commands;

import data.Experience;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

public class AddExpCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        if(PermissionUtil.checkPermission(e.getMember(), Permission.ADMINISTRATOR)){
            int numExp;
            String userId;
            if(message.length > 2){
                numExp = Integer.parseInt(message[1]);
                Member m = e.getMessage().getMentionedMembers().get(0);
                userId = m.getId();
                e.getChannel().sendMessage("Added " + numExp + " experience to " + e.getGuild().getMemberById(userId).getAsMention() + ".").queue();
                Experience.addExp(e,numExp, m, false);
            }
            else if(message.length > 1){
                numExp = Integer.parseInt(message[1]);
                e.getChannel().sendMessage("Added " + numExp + " experience to " + e.getMember().getAsMention() + ".").queue();
                Experience.addExp(e, numExp, false);
            }
            else{
                e.getChannel().sendMessage("> Please enter parameters.");
            }
        }
        else{
            e.getChannel().sendMessage("> You do not have permission for this command");
        }
    }
}
