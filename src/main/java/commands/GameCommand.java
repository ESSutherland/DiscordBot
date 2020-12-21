package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.FileOutputStream;
import java.io.IOException;

public class GameCommand {
    public static void command(GuildMessageReceivedEvent e, String[] message){
        e.getMessage().delete().complete();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Community Games!");
        eb.setDescription("React to this message with :video_game: to recieve the " + e.getGuild().getRoleById(Data.prop.getProperty("gameRoleId")).getAsMention() +
                " role to be notified about Community Gaming related messages!");
        eb.setColor(Data.botColor);
        eb.setFooter(Data.authorFooter);

        e.getChannel().sendMessage(eb.build()).complete();

        MessageChannel msgChannel = e.getChannel();
        msgChannel.getHistory().retrievePast(1).queue(messages -> {
            for(Message msg: messages){
                Data.prop.setProperty("gameMessageId", msg.getId());
                try {
                    Data.prop.store(new FileOutputStream(Data.propFile), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                msg.addReaction("\uD83C\uDFAE").queue();
            }
        });
    }
}
