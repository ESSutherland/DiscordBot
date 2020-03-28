package commands;

import data.Modules;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.sql.SQLException;

public class ModuleCommand {
    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {

        String module = message[1];
        String enable = message[2];
        String out = "> Module " + module;

        if(Modules.findModule(module)){
            if(enable.equalsIgnoreCase("disable")){
                if(Modules.isModuleEnabled(module)){
                    out += " disabled.";
                    Modules.enableModule(0, module.toLowerCase());
                }
                else{
                    out += " is not enabled.";
                }
            }
            else if (enable.equalsIgnoreCase("enable")){
                if(Modules.isModuleEnabled(module)){
                    out += " already enabled.";
                }
                else {
                    out += " enabled.";
                    Modules.enableModule(1, module.toLowerCase());
                }
            }
            else{
                out = "Invalid parameter: " + enable;
            }
        }
        else{
            out += " does not exist.";
        }
        e.getChannel().sendMessage(out).queue();
    }
}
