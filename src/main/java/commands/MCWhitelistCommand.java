package commands;

import data.CommandEmbed;
import data.Data;
import io.graversen.minecraft.rcon.MinecraftRcon;
import io.graversen.minecraft.rcon.commands.WhiteListCommand;
import io.graversen.minecraft.rcon.service.ConnectOptions;
import io.graversen.minecraft.rcon.service.MinecraftRconService;
import io.graversen.minecraft.rcon.service.RconDetails;
import io.graversen.minecraft.rcon.util.Target;
import io.graversen.minecraft.rcon.util.WhiteListModes;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.shanerx.mojang.Mojang;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class MCWhitelistCommand {
    static MinecraftRcon minecraftRcon;
    static MinecraftRconService minecraftRconService;
    static Mojang api = new Mojang().connect();
    public static void connect(){
        minecraftRconService = new MinecraftRconService(new RconDetails(Data.prop.getProperty("minecraftRconIP"), Integer.parseInt(Data.prop.getProperty("minecraftRconPort")), Data.prop.getProperty("minecraftRconPass")),
                ConnectOptions.defaults());
        minecraftRconService.connectBlocking(Duration.ofSeconds(5));
        minecraftRcon = minecraftRconService.minecraftRcon().get();
    }

    public static void command(GuildMessageReceivedEvent e, String[] message){
        if(api.getStatus(Mojang.ServiceType.AUTHSERVER_MOJANG_COM) != Mojang.ServiceStatus.GREEN){
            System.err.println("The Auth Server is not available right now.");
            CommandEmbed.errorEB(e, "> Mojang Auth Server Not Avaliable.");
        }
        else{
            try {
                if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId"))) ||
                        e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("modRoleId")))){
                    final WhiteListCommand whitelist;
                    String mcUsername = "";
                    String userId = e.getMember().getUser().getId();

                    if(message.length < 2){
                        CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): !whitelist {username}");
                    }
                    else {
                        e.getChannel().sendMessage("> Getting Info From Server, Please Wait...").queue(message1 -> message1.delete().queueAfter(5, TimeUnit.SECONDS));
                        mcUsername = message[1];

                        try {
                            api.getUUIDOfUsername(mcUsername);
                            connect();
                            if (Data.findUserInDB(userId)) {
                                System.out.println("USER FOUND");
                                if (Data.getDBUser(userId).getMcUsername() != null) {
                                    unWhitelist(userId);
                                    CommandEmbed.successEB(e, "Updated Whitelist for " + e.getMember().getAsMention() + ": " + mcUsername);
                                } else {
                                    CommandEmbed.successEB(e, "Added Whitelist for " + e.getMember().getAsMention() + ": " + mcUsername);
                                }
                                Data.updateMCUserName(mcUsername, userId);
                                whitelist = new WhiteListCommand(Target.player(mcUsername), WhiteListModes.ADD);
                                minecraftRcon.sendSync(whitelist);
                                disconnect();
                            }
                        } catch (NullPointerException ex) {
                            CommandEmbed.errorEB(e, "> " + mcUsername + " is not a valid Minecraft Account.");
                        }
                    }
                }
                else{
                    CommandEmbed.errorEB(e, "You must be a subscriber to whitelist an account.");
                }
            }
            catch (NoSuchElementException ex){
                CommandEmbed.errorEB(e, "Server is offline.");
            }
        }
    }

    public static void unWhitelist(String userId){
        WhiteListCommand removeWL;
        removeWL = new WhiteListCommand(Target.player(Data.getDBUser(userId).getMcUsername()), WhiteListModes.REMOVE);
        minecraftRcon.sendSync(removeWL);
    }

    public static void disconnect(){
        minecraftRconService.disconnect();
    }
}
