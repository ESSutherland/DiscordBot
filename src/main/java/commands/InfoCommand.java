package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;

public class InfoCommand {

    public static void command(GuildMessageReceivedEvent e, String[] message){

        if(message.length > 1){
            for(Member member: e.getMessage().getMentionedMembers()){
                buildEB(member, e);
            }
        }
        else{
            buildEB(e.getMember(), e);
        }
    }

    private static void buildEB(Member member, GuildMessageReceivedEvent e){

        String userId = member.getId();
        String userName = member.getUser().getName();

        EmbedBuilder eb = new EmbedBuilder();
        String format = "E, MMM d, yyyy hh:mm a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        eb.setColor(member.getColor());
        eb.setAuthor(member.getUser().getAsTag(), null, member.getUser().getAvatarUrl());
        eb.setDescription(member.getAsMention());
        eb.addField("Joined", member.getTimeJoined().format(formatter), true);
        eb.addBlankField(true);
        eb.addField("Registered", member.getTimeCreated().format(formatter), true);

        String roles = "";
        for(Role r: member.getRoles()){
            roles += r.getAsMention() + " ";
        }

        eb.addField("Roles [" + member.getRoles().size() + "]", roles, false);

        String perms = "";
        for(Permission r: member.getPermissions()){
            if(!e.getGuild().getRoleById(Data.prop.getProperty("userRoleId")).getPermissions().contains(r)){
                perms += r.getName() + ", ";
            }
        }
        if(perms.length() > 1){
            perms = perms.substring(0, perms.length() - 2);
            eb.addField("Special Permissions", perms, false);
        }
        eb.addField("ID", userId, true);
        eb.setThumbnail(member.getUser().getAvatarUrl());
        eb.setFooter("Bot by SpiderPigEthan");

        e.getChannel().sendMessage(eb.build()).queue();
    }
}
