package events;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import data.Data;
import data.Modules;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class LiveAnnouncement {

    public static void goLiveSub(SimpleEventHandler eventHandler, JDA jda){
        eventHandler.onEvent(ChannelGoLiveEvent.class, event -> {
            try {
                if(Modules.isModuleEnabled("stream_announcement")){
                    onGoLive(event, jda);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static void onGoLive(ChannelGoLiveEvent event, JDA jda) {
        try{
            TextChannel channel = jda.getTextChannelById(Data.prop.getProperty("announceChannelId"));
            System.out.println(event.getChannel().getName() + " LIVE");

            String url = "https://api.twitch.tv/helix/";

            HttpResponse<JsonNode> userData = Unirest.get(url + "users?id=" + event.getChannel().getId()).header("Client-ID", Data.prop.getProperty("twitchClientId")).asJson();
            HttpResponse<JsonNode> gameData = Unirest.get(url + "games?id=" + event.getGameId()).header("Client-ID", Data.prop.getProperty("twitchClientId")).asJson();

            JSONObject userObj = userData.getBody().getObject().getJSONArray("data").getJSONObject(0);
            JSONObject gameObj = gameData.getBody().getObject().getJSONArray("data").getJSONObject(0);

            String name = userObj.getString("display_name");
            String img = userObj.getString("profile_image_url");
            String game = gameObj.getString("name");

            channel.sendMessage("@everyone").complete();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Data.botColor);
            eb.setAuthor("Twitch", "https://www.twitch.tv/", "http://assets.stickpng.com/images/580b57fcd9996e24bc43c540.png");
            eb.setTitle("**" + name + " is live on Twitch!**");
            eb.setDescription(event.getTitle());
            eb.addField("Game", game, true);
            eb.addBlankField(true);
            eb.addField("Stream", "https://www.twitch.tv/" + event.getChannel().getName(), true);

            SimpleDateFormat format = new SimpleDateFormat("E, MMM d, yyyy hh:mm a z");
            format.setCalendar(event.getFiredAt());

            eb.addField("Stream Start Time", format.format(event.getFiredAt().getTime()), false);
            eb.setThumbnail(img);
            eb.setFooter("Bot by SpiderPigEthan");

            channel.sendMessage(eb.build()).complete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
