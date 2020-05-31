package data;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FandomApi {

    public FandomApi(String[] name, GuildMessageReceivedEvent e){
        //System.out.println(formatName(name));
        try{
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            Unirest.setHttpClient(httpClient);

            HttpResponse<JsonNode> response = Unirest.get("https://nopixel.fandom.com/api/v1/Search/List")
                    .queryString("query", formatName(name))
                    .queryString("limit", 5)
                    .asJson();
            JSONObject result = null;

            int bestMatch = 0;
            int bestMatchId = 0;

            for(int i = 0; response.getBody().getObject().getJSONArray("items").length() > i; i++){
                 result = response.getBody().getObject().getJSONArray("items").getJSONObject(i);
                 List<String> wordList = Arrays.asList(result.getString("title").split(" "));
                 int match = 0;
                 for(int j = 0; j < wordList.size(); j++){
                     for(int k = 0; k < name.length; k++){
                         if(wordList.get(j).toLowerCase().contains(name[k].toLowerCase())){
                             match++;
                         }
                     }
                 }
                 if(match > bestMatch){
                     bestMatch = match;
                     bestMatchId = i;
                 }
            }
            result = response.getBody().getObject().getJSONArray("items").getJSONObject(bestMatchId);

            int id = result.getInt("id");
            String url = result.getString("url");

            HttpResponse<JsonNode> httpResponse = Unirest.get("https://nopixel.fandom.com/api/v1/Articles/Details")
                    .queryString("ids", id)
                    .asJson();

            JSONObject obj = httpResponse.getBody().getObject().getJSONObject("items");
            JSONObject details = obj.getJSONObject(obj.keySet().toArray()[0].toString());

            //System.out.println(httpResponse.getBody().toString());

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Data.botColor);
            eb.setAuthor("NoPixel Wiki", "https://nopixel.fandom.com/");
            eb.setTitle(details.getString("title"));
            eb.setThumbnail(details.getString("thumbnail"));
            eb.setDescription(details.getString("abstract"));
            eb.addField("Link", url, true);
            eb.setFooter("Bot by SpiderPigEthan");


            e.getChannel().sendMessage(eb.build()).queue();
        }
        catch (Exception ex){
            ex.printStackTrace();
            CommandEmbed.errorEB(e, "Entry not found");
        }

    }

    private String formatName(String[] name){

        String formatted = "";

        for(int i = 1; i < name.length; i++){
            String s;
            //s = name[i].substring(0, 1).toUpperCase() + name[i].substring(1);
            formatted += name[i];
            if(i < name.length-1){
                formatted += ",";
            }
        }

        return formatted;
    }
}
