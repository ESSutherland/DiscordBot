import commands.Commands;
import data.Data;
import data.Modules;
import events.DeleteRole;
import events.UserJoin;
import events.UserLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import java.util.EnumSet;

public class Main {
    public static void main(String args[]) throws Exception{
        Data.connectDB();
        Modules.connectDB();
        Data.loadData();

        JDA jda = JDABuilder.createDefault(Data.prop.getProperty("token"), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGES)
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.VOICE_STATE, CacheFlag.EMOTE))
                .setActivity(Activity.playing(Data.prop.getProperty("playingStatus"))).build();
        jda.addEventListener(new UserJoin());
        jda.addEventListener(new UserLeave());
        jda.addEventListener(new Commands());
        jda.addEventListener(new DeleteRole());
    }
}