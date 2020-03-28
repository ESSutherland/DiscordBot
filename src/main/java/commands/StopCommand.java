package commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class StopCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        e.getChannel().sendMessage("> Stopping...").queue();
        e.getJDA().shutdown();
    }
}
