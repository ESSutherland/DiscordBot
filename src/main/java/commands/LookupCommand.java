package commands;

import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;

public class LookupCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        if(message.length > 1){
            String userId = message[1];
            try {
                User user = e.getJDA().retrieveUserById(userId).complete();

                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor(user.getAsTag() + " - " + userId);
                //eb.setTitle();
                eb.setThumbnail(user.getEffectiveAvatarUrl());
                eb.setDescription(user.getAsMention());
                String format = "E, MMM d, yyyy hh:mm a O";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                eb.addField("Registered", user.getTimeCreated().format(formatter), true);
                eb.setFooter(Data.authorFooter);
                eb.setColor(Data.botColor);
                e.getChannel().sendMessage(eb.build()).queue();
            }
            catch (Exception ex){
                ex.printStackTrace();
                CommandEmbed.errorEB(e,"`" + userId + "` is not a valid Discord user ID.");
            }
        }
    }
}
