package commands;

import data.ACVillager;
import data.AnimalCrossingAPI;
import data.CommandEmbed;
import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class VillagerCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){

        if(message.length < 2){
            CommandEmbed.errorEB(e, "Please use correct parameters ({}=required) " + Data.PREFIX + "villager {name}");
        }
        else{
            String villagerName = message[1];
            ACVillager villager = null;

            for(ACVillager v: AnimalCrossingAPI.villagers){
                if(villagerName.equalsIgnoreCase(v.getName())){
                    villager = v;
                }
            }

            if(villager != null){
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Data.botColor);
                eb.setAuthor(villager.getName(), null,  villager.getIcon());
                //eb.setTitle(villager.getName());
                eb.setThumbnail(villager.getIcon());
                eb.addField("Personality :smiley:", villager.getPersonality(), true);
                eb.addBlankField(true);
                eb.addField("Birthday :birthday:", villager.getBirthday(), true);
                eb.addField("Species :dna:", villager.getSpecies(), true);
                eb.addBlankField(true);
                eb.addField("Gender :couple:", villager.getGender(), true);
                eb.addField("Catchphrase :speech_balloon:", villager.getCatchphrase(), false);
                eb.setImage("http://acnhapi.com/images/villagers/" + villager.getId());
                eb.setFooter("Bot by SpiderPigEthan");
                e.getChannel().sendMessage(eb.build()).queue();
            }
            else{
                CommandEmbed.errorEB(e, "Villager not Found! This command only supports villagers from Animal Crossing: New Horizons.");
            }
        }

    }
}
