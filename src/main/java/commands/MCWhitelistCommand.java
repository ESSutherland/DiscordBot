package commands;

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

public class MCWhitelistCommand {
    static MinecraftRcon minecraftRcon;
    static MinecraftRconService minecraftRconService;
    static Mojang api = new Mojang().connect();
    public static void connect(){
        minecraftRconService = new MinecraftRconService(new RconDetails(Data.prop.getProperty("minecraftRconIP"), Integer.parseInt(Data.prop.getProperty("minecraftRconPort")), Data.prop.getProperty("minecraftRconPass")),
                ConnectOptions.neverStopTrying());
        minecraftRconService.connectBlocking(Duration.ofSeconds(5));
        minecraftRcon = minecraftRconService.minecraftRcon().get();
    }

    public static void command(GuildMessageReceivedEvent e, String[] message){
        if(api.getStatus(Mojang.ServiceType.AUTHSERVER_MOJANG_COM) != Mojang.ServiceStatus.GREEN){
            System.err.println("The Auth Server is not available right now.");
            e.getChannel().sendMessage("> Mojang Auth Server Not Avaliable.").queue();
        }
        else{
            if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId")))){
                final WhiteListCommand whitelist;
                String mcUsername = "";
                String userId = e.getMember().getUser().getId();

                if(message.length < 2){
                    e.getChannel().sendMessage("> Please include your Minecraft Username.").queue();
                }
                else {
                    e.getChannel().sendMessage("> Getting info from Server.").queue();
                    mcUsername = message[1];

                    try{
                        api.getUUIDOfUsername(mcUsername);
                        connect();
                        if(Data.findUserInDB(userId)){
                            System.out.println("USER FOUND");
                            if(Data.getDBUser(userId).getMcUsername() != null){
                                unWhitelist(userId);
                                e.getChannel().sendMessage("> Updated Whitelist for " + e.getMember().getAsMention() + ": " + mcUsername).queue();
                            }
                            else{
                                e.getChannel().sendMessage("> Added Whitelist for " + e.getMember().getAsMention() + ": " + mcUsername).queue();
                            }
                            Data.updateMCUserName(mcUsername, userId);
                            whitelist = new WhiteListCommand(Target.player(mcUsername), WhiteListModes.ADD);
                            minecraftRcon.sendSync(whitelist);
                            disconnect();
                        }
                    }
                    catch (NullPointerException ex){
                        e.getChannel().sendMessage("> " + mcUsername + " is not a valid Minecraft Account.").queue();
                    }
                }
            }
            else{
                e.getChannel().sendMessage("> You must be a subscriber to whitelist an account.").queue();
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
