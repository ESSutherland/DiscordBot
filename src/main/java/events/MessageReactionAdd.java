package events;

import data.Data;
import data.Modules;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.SQLException;

public class MessageReactionAdd extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent e) {

        if(!e.getUser().isBot()){
            try {
                if(Modules.isModuleEnabled("movie_night") && e.getMessageId().equalsIgnoreCase(Data.prop.getProperty("movieMessageId")) && e.getReactionEmote().isEmoji()){
                    if(e.getReactionEmote().getEmoji().equals("\uD83C\uDFA5")){
                        e.getGuild().addRoleToMember(e.getUser().getId(), e.getGuild().getRoleById(Data.prop.getProperty("movieRoleId"))).queue();
                    }
                    //🎥
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
