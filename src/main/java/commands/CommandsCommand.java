package commands;

import data.CommandData;
import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class CommandsCommand {

    public static final int COMMANDS_PER_PAGE = 6;

    public static void command(GuildMessageReceivedEvent e, String[] message) throws SQLException {

        int page;
        int totalPages;
        try {
            ArrayList<String[]> commandList = CommandData.getCommands();

            if(message.length < 2){
                page = 1;
            }
            else{
                page = Integer.parseInt(message[1]);
            }

            totalPages = (int)Math.ceil((double)commandList.size()/(double)COMMANDS_PER_PAGE);

            if(totalPages < page){
                CommandEmbed.errorEB(e, "That page does not exist. Last page is `" + totalPages + "`.");
            }
            else if (page < 1){
                CommandEmbed.errorEB(e, "That page does not exist. First page is `1`.");
            }
            else{
                if (commandList.size() > 0) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Data.botColor);
                    eb.setTitle("Custom Commands");

                    int number = (Math.min((COMMANDS_PER_PAGE * page), commandList.size()));

                    for (int i = (COMMANDS_PER_PAGE * (page - 1)); i < number; i++) {
                        try{
                            String perms;
                            switch (commandList.get(i)[2]) {
                                case "-a":
                                    perms = "Everyone";
                                    break;
                                case "-b":
                                    perms = "Nitro Boosters";
                                    break;
                                case "-s":
                                    perms = "Subscribers";
                                    break;
                                case "-m":
                                    perms = "Moderators";
                                    break;
                                default:
                                    perms = e.getGuild().getMemberById(commandList.get(i)[2]).getAsMention();
                            }

                            if(i == (COMMANDS_PER_PAGE * (page - 1))){
                                eb.addField("Command", commandList.get(i)[0], true);
                                eb.addField("Response", commandList.get(i)[1], true);
                                eb.addField("Permission", perms, true);
                            }
                            else {
                                eb.addField("", commandList.get(i)[0], true);
                                eb.addField("", commandList.get(i)[1], true);
                                eb.addField("", perms, true);
                            }
                        }
                        catch (IndexOutOfBoundsException ex){
                            break;
                        }
                    }
                    eb.addField("", "Page [" + page +"/" + totalPages + "]", true);
                    eb.setFooter(Data.authorFooter);
                    e.getChannel().sendMessage(eb.build()).queue();
                }
                else {
                    CommandEmbed.errorEB(e, "There are no commands.");
                }
            }
        }
        catch (NumberFormatException ex){
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required): `" + Data.PREFIX + "commands {page number}`");
        }
    }
}
