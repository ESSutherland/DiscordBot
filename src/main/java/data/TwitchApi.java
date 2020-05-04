package data;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import events.LiveAnnouncement;
import net.dv8tion.jda.api.JDA;

public class TwitchApi {

    public TwitchClient twitchClient;

    public TwitchApi(){
        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        twitchClient = clientBuilder
                .withEnableHelix(true)
                .withEnableKraken(true)
                .withClientId(Data.prop.getProperty("twitchClientId"))
                .withClientSecret(Data.prop.getProperty("twitchClientSecret"))
                .build();

        twitchClient.getClientHelper().enableStreamEventListener(Data.prop.getProperty("twitchName"));
    }


    public void registerFeatures(JDA jda){
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        LiveAnnouncement.goLiveSub(eventHandler, jda);
    }
}
