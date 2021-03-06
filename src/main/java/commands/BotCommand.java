package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class BotCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.setTitle("Bot Information");
        eb.setAuthor("SpiderPigEthan", null, e.getGuild().getMemberById("106882411781947392").getUser().getAvatarUrl());
        eb.setDescription("A custom bot created by SpiderPigEthan. Written in Java using Java Discord API.");
        eb.setFooter(Data.authorFooter);
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
