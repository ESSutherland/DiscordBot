import data.CommandData;
import events.*;
import data.Data;
import data.Modules;
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

        JDA jda = JDABuilder.createDefault(Data.prop.getProperty("token"), EnumSet.allOf(GatewayIntent.class))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setActivity(Activity.playing(Data.prop.getProperty("playingStatus"))).build();
        jda.addEventListener(new UserJoin());
        jda.addEventListener(new UserLeave());
        jda.addEventListener(new Message());
        jda.addEventListener(new DeleteRole());
        jda.addEventListener(new AddRole());
    }
}