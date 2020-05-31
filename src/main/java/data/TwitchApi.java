package data;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import events.LiveAnnouncement;
import net.dv8tion.jda.api.JDA;

public class TwitchApi {

    public TwitchClient twitchClient;

    public String accessToken;

    public TwitchApi(){
        //System.out.println("TwitchApi");
        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        CredentialManager credentialManager = CredentialManagerBuilder.builder().build();

        credentialManager.getOAuth2CredentialByUserId(Data.prop.getProperty("twitchClientId"));

        TwitchIdentityProvider provider = new TwitchIdentityProvider(Data.prop.getProperty("twitchClientId"), Data.prop.getProperty("twitchClientSecret"), "");
        credentialManager.registerIdentityProvider(provider);

        accessToken = provider.getAppAccessToken().getAccessToken();

        twitchClient = clientBuilder
                .withEnableHelix(true)
                .withCredentialManager(credentialManager)
                .withDefaultAuthToken(provider.getAppAccessToken())
                .build();

        twitchClient.getEventManager().registerEventHandler(new SimpleEventHandler());
        twitchClient.getClientHelper().enableStreamEventListener(Data.prop.getProperty("twitchName"));
    }


    public void registerFeatures(JDA jda){
        //System.out.println("registerFeatures");
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        LiveAnnouncement live = new LiveAnnouncement(eventHandler, jda, twitchClient, accessToken);
    }
}
