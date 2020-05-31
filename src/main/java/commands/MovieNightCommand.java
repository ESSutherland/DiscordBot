package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.FileOutputStream;
import java.io.IOException;

public class MovieNightCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){
        e.getMessage().delete().complete();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Movie Night!");
        eb.setDescription("React to this message with :movie_camera: to recieve the " + e.getGuild().getRoleById(Data.prop.getProperty("movieRoleId")).getAsMention() +
                " role to be notified about Movie Night related messages!");
        eb.setColor(Data.botColor);
        eb.setFooter("Bot by SpiderPigEthan");

        e.getChannel().sendMessage(eb.build()).complete();

        MessageChannel msgChannel = e.getChannel();
        msgChannel.getHistory().retrievePast(1).queue(messages -> {
            for(Message msg: messages){
                Data.prop.setProperty("movieMessageId", msg.getId());
                try {
                    Data.prop.store(new FileOutputStream(Data.propFile), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                msg.addReaction("\uD83C\uDFA5").queue();
            }
        });
    }
}
