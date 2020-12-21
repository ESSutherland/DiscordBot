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
import org.json.JSONArray;
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

            /*HttpResponse<JsonNode> response = Unirest.get("https://nopixel.fandom.com/api/v1/Search/List")
                    .queryString("query", formatName(name))
                    .queryString("limit", 5)
                    .asJson();
            JSONObject result = null;*/

            HttpResponse<JsonNode> response = Unirest.get("https://nopixel.fandom.com/api.php?action=query&list=search&srsearch=" + formatName(name) + "&format=json").asJson();

            JSONObject result = null;

            //System.out.println(response.getBody().toString());

            JSONArray array = response.getBody().getObject().getJSONObject("query").getJSONArray("search");
            System.out.println(array.toString());

            int bestMatch = 0;
            int bestMatchId = 0;

            for(int i = 0; array.length() > i; i++){
                 result = array.getJSONObject(i);
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
            result = array.getJSONObject(bestMatchId);

            int id = result.getInt("pageid");

            HttpResponse<JsonNode> httpResponse = Unirest.get("https://nopixel.fandom.com/api.php?action=query&pageids=" + id + "&format=json")
                    .asJson();

            JSONObject obj = httpResponse.getBody().getObject().getJSONObject("query");
            JSONObject page = obj.getJSONObject("pages").getJSONObject(id + "");
            //JSONArray images = obj.getJSONArray("images");

            System.out.println(page.toString());
            String url = "https://nopixel.fandom.com/" + page.getString("title").replace(" ", "%20");

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Data.botColor);
            eb.setAuthor("NoPixel Wiki", "https://nopixel.fandom.com/");
            eb.setTitle(page.getString("title"));
            //eb.setThumbnail(details.getString("thumbnail"));
            //eb.setDescription(details.getString("abstract"));
            eb.addField("Link", url, true);
            eb.setFooter(Data.authorFooter);


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
