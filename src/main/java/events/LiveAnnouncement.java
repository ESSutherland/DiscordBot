package events;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import com.github.twitch4j.common.events.channel.ChannelGoOfflineEvent;
import com.github.twitch4j.helix.domain.Game;
import com.github.twitch4j.helix.domain.User;
import data.Data;
import data.Modules;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LiveAnnouncement {

     JDA jda;
     TwitchClient twitchClient;
     String accessToken;

    public LiveAnnouncement(SimpleEventHandler eventHandler, JDA jda, TwitchClient twitchClient, String accessToken){
        this.jda = jda;
        this.twitchClient = twitchClient;
        this.accessToken = accessToken;
        eventHandler.onEvent(ChannelGoLiveEvent.class, event -> {
            try {
                if(Modules.isModuleEnabled("stream_announcement")){
                    onGoLive(event);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        eventHandler.onEvent(ChannelGoOfflineEvent.class, channelGoOfflineEvent -> {
            try {
                if(Modules.isModuleEnabled("stream_announcement")){
                    ArrayList<net.dv8tion.jda.api.entities.Message> messages = new ArrayList<>();
                    MessageHistory history = new MessageHistory(jda.getTextChannelById(Data.prop.getProperty("announceChannelId")));
                    List<net.dv8tion.jda.api.entities.Message> pastMessages = history.retrievePast(10).complete();
                    for(Message m: pastMessages){
                        if(m.getAuthor().equals(jda.getSelfUser())){
                            messages.add(m);
                        }

                        if(messages.size() == 2){
                            break;
                        }
                    }
                    jda.getTextChannelById(Data.prop.getProperty("announceChannelId")).deleteMessages(messages).queue();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void onGoLive(ChannelGoLiveEvent event) {
        try{
            TextChannel channel = jda.getTextChannelById(Data.prop.getProperty("announceChannelId"));
            System.out.println(event.getChannel().getName() + " LIVE");

            List<User> users = twitchClient.getHelix().getUsers(accessToken, Arrays.asList(event.getChannel().getId()),null).execute().getUsers();
            List<Game> games = twitchClient.getHelix().getGames(accessToken, Arrays.asList(event.getGameId()),null).execute().getGames();

            User user = users.get(0);
            Game game = games.get(0);

            channel.sendMessage("@everyone " + user.getDisplayName() + " is live now, playing " + game.getName()).complete();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Data.botColor);
            eb.setAuthor("Twitch", "https://www.twitch.tv/", "http://assets.stickpng.com/images/580b57fcd9996e24bc43c540.png");
            eb.setTitle("**" + user.getDisplayName() + " is live on Twitch!**", "https://www.twitch.tv/" + event.getChannel().getName());
            eb.setDescription(event.getTitle());
            eb.addField("Game", game.getName(), true);
            eb.addBlankField(true);
            eb.addField("Stream", "https://www.twitch.tv/" + event.getChannel().getName(), true);

            SimpleDateFormat format = new SimpleDateFormat("E, MMM d, yyyy hh:mm a z");

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                            .withLocale( Locale.UK )
                            .withZone(ZoneId.systemDefault());

            format.setCalendar(event.getFiredAt());

            System.out.println(formatter.format(event.getFiredAt().getTime().toInstant()));

            eb.addField("Stream Start Time", formatter.format(event.getFiredAt().getTime().toInstant()) + " "  + formatter.getZone().getDisplayName(TextStyle.SHORT, formatter.getLocale()), false);
            eb.setThumbnail(user.getProfileImageUrl());
            eb.setFooter(Data.authorFooter);

            channel.sendMessage(eb.build()).complete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
