import commands.LoadUsersCommand;
import data.*;
import events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.EnumSet;


public class Main {
    public static void main(String args[]) throws Exception{
        Data.connectDB();
        Modules.connectDB();
        CommandData.connectDB();
        Data.loadData();
        AnimalCrossingAPI.connect();

        JDA jda = JDABuilder.createDefault(Data.prop.getProperty("token"), EnumSet.allOf(GatewayIntent.class))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setActivity(Activity.playing(Data.prop.getProperty("playingStatus") + " | " + Data.PREFIX + "help")).build();
        jda.addEventListener(new UserJoin());
        jda.addEventListener(new UserLeave());
        jda.addEventListener(new Message());
        jda.addEventListener(new DeleteRole());
        jda.addEventListener(new AddRole());
        jda.addEventListener(new MessageReactionAdd());
        jda.addEventListener(new MessageReactionRemove());
        jda.addEventListener(new UserBan());

        jda.awaitReady();

        TwitchApi api = new TwitchApi();
        api.registerFeatures(jda);

        LoadUsersCommand.startup(jda);
    }
}