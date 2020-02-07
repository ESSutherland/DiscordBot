import commands.ColorReqCommand;
import data.Data;
import events.DeleteRole;
import events.JoinRole;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {
    public static void main(String args[]) throws Exception{

        Data.loadData();

        JDA jda = new JDABuilder(Data.prop.getProperty("token")).setGuildSubscriptionsEnabled(true).setActivity(Activity.playing(Data.prop.getProperty("playingStatus"))).build();
        jda.addEventListener(new ColorReqCommand());
        jda.addEventListener(new DeleteRole());
        jda.addEventListener(new JoinRole());
    }
}
