package commands;

import data.FandomApi;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class WikiCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        FandomApi api = new FandomApi(message, e);
    }
}
