package commands;

import data.CommandEmbed;
import data.Modules;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;

public class ModuleCommand {
    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {
        if(message.length < 3){
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): !module {name} {enable/disable}");
        }
        else{
            String module = message[1];
            String enable = message[2];
            String out = "Module " + module;

            if(Modules.findModule(module)){
                if(enable.equalsIgnoreCase("disable")){
                    if(Modules.isModuleEnabled(module)){
                        out += " disabled.";
                        CommandEmbed.successEB(e, out);
                        Modules.enableModule(0, module.toLowerCase());
                    }
                    else{
                        out += " is not enabled.";
                        CommandEmbed.errorEB(e, out);
                    }
                }
                else if (enable.equalsIgnoreCase("enable")){
                    if(Modules.isModuleEnabled(module)){
                        out += " already enabled.";
                        CommandEmbed.errorEB(e, out);
                    }
                    else {
                        out += " enabled.";
                        CommandEmbed.successEB(e, out);
                        Modules.enableModule(1, module.toLowerCase());
                    }
                }
                else{
                    CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): !module {name} {enable/disable}");
                }
            }
            else{
                out += " does not exist.";
                CommandEmbed.errorEB(e, out);
            }
        }
    }
}
