package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class LevelCommand {

    public static void command(GuildMessageReceivedEvent e, String message[]){

        Member member;
        String userId;
        String userName;
        if (message.length > 1 && e.getMessage().getMentionedMembers().size() > 0) {
            for(Member m: e.getMessage().getMentionedMembers()) {
                userId = m.getId();
                userName = m.getUser().getName();
                buildEB(userName, userId, m, e);
            }
        }
        else{
            userId = e.getMember().getUser().getId();
            userName = e.getMember().getUser().getName();
            member = e.getMember();
            buildEB(userName, userId, member, e);
        }

    }

    private static void buildEB(String userName, String userId, Member member, GuildMessageReceivedEvent e){
        int level;
        double exp;
        int userRank;

        level = Data.getUserLevel(userId);
        exp = Data.getUserExp(userId);
        userRank = Data.getUserRank(userId);
        String levelExp = Data.prop.getProperty("levelExp");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.setTitle("Rank: #" + userRank);
        eb.setAuthor(userName, null, member.getUser().getAvatarUrl());
        eb.addField("Level", "" + Data.getUserLevel(userId), true);
        eb.addBlankField(true);
        eb.setThumbnail(Data.prop.getProperty("levelImg"));
        eb.addField("Exp to level " + (level + 1), exp + "/" + levelExp, true);
        eb.setFooter(Data.authorFooter);
        if(member.getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")))){
            eb.setDescription("Multiplier: **" + e.getGuild().getRoleById(Data.prop.getProperty("nitroRoleId")).getName() + "(x" + Data.prop.getProperty("nitroExp") + ")**");
        }
        else if(member.getRoles().contains(e.getGuild().getRoleById(Data.prop.getProperty("subRoleId")))){
            eb.setDescription("Multiplier: **" + e.getGuild().getRoleById(Data.prop.getProperty("subRoleId")).getName() + "(x" + Data.prop.getProperty("subExp") + ")**");
        }
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
