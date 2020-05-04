package commands;

import data.Data;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AgreeCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        e.getGuild().removeRoleFromMember(e.getMember(), e.getGuild().getRoleById(Data.prop.getProperty("defaultRoleId"))).queue();
        e.getGuild().addRoleToMember(e.getMember(), e.getGuild().getRoleById(Data.prop.getProperty("userRoleId"))).queue();
    }
}
