import commands.ColorReqCommand;
import data.Data;
import events.DeleteRole;
import events.JoinRole;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws Exception{
        Scanner in = new Scanner(System.in);

        Data.loadData();

        try {
            FileInputStream ip = new FileInputStream("config.properties");
            Data.prop.load(ip);
        }
        catch (FileNotFoundException e){

        }

        JDA jda = new JDABuilder(Data.prop.getProperty("token")).setGuildSubscriptionsEnabled(true).setActivity(Activity.playing("In The Snow.")).build();
        jda.addEventListener(new ColorReqCommand());
        jda.addEventListener(new DeleteRole());
        jda.addEventListener(new JoinRole());
    }
}
