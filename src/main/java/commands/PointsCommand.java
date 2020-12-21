package commands;

import data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PointsCommand {
    /*public static void command(GuildMessageReceivedEvent e, String[] message){
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
        //int points = Data.getUserPoints(userId);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Data.botColor);
        eb.setAuthor(userName, null, member.getUser().getAvatarUrl());
        //eb.addField("Points", "" + points, true);
        eb.addBlankField(true);
        eb.setFooter(Data.authorFooter);
        e.getChannel().sendMessage(eb.build()).queue();
    }*/
}
