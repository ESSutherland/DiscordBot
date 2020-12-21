package commands;

import data.CommandEmbed;
import data.Data;
import data.Modules;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class ModulesCommand {

    public static final int MODULES_PER_PAGE = 6;

    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {

        int page;
        int totalPages;

        try{
            ArrayList<String[]> modulesList  = Modules.getModules();

            if(message.length < 2){
                page = 1;
            }
            else{
                page = Integer.parseInt(message[1]);
            }

            totalPages = (int)Math.ceil((double)modulesList.size()/(double)MODULES_PER_PAGE);


            if(totalPages < page){
                CommandEmbed.errorEB(e, "That page does not exist. Last page is `" + totalPages + "`.");
            }
            else if (page < 1){
                CommandEmbed.errorEB(e, "That page does not exist. First page is `1`.");
            }
            else{
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Data.botColor);
                eb.setTitle("Modules");


                int number = (Math.min((MODULES_PER_PAGE * page), modulesList.size()));

                for (int i = (MODULES_PER_PAGE * (page - 1)); i < number; i++) {
                    try{
                        String enabled = (modulesList.get(i)[1].equalsIgnoreCase("1") ? "Enabled" : "Disbaled");

                        if(i == (MODULES_PER_PAGE * (page - 1))){
                            eb.addField("Module", modulesList.get(i)[0], true);
                            //eb.addBlankField(true);
                            eb.addField("Description", modulesList.get(i)[2], true);
                            //eb.addBlankField(true);
                            eb.addField("Status", enabled, true);
                        }
                        else {
                            eb.addField("", modulesList.get(i)[0], true);
                            //eb.addBlankField(true);
                            eb.addField("", modulesList.get(i)[2], true);
                            //eb.addBlankField(true);
                            eb.addField("", enabled, true);
                        }
                    }
                    catch (IndexOutOfBoundsException ex){
                        break;
                    }
                }
                //eb.addBlankField(true);
                eb.addField("", "Page [" + page +"/" + totalPages + "]", true);
                // eb.addBlankField(true);

        /*for(String[] s: modulesList){
            String enabled = "";
            if(s[1].equalsIgnoreCase("0"))
                enabled = "disabled";
            else if(s[1].equalsIgnoreCase("1"))
                enabled = "enabled";

            eb.addField(s[0], enabled, false);
        }*/
                eb.setFooter(Data.authorFooter);
                e.getChannel().sendMessage(eb.build()).queue();
            }
        }
        catch (NumberFormatException ex){
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): `" + Data.PREFIX + "modules {page number}`");
        }
    }
}
