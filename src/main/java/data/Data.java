package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Data {

    public static ArrayList<String> userIds = new ArrayList<String>();
    public static ArrayList<String> roleIds = new ArrayList<String>();
    public static ArrayList<String> userNames = new ArrayList<String>();
    public static Properties prop = new Properties();

    public static void loadData(){
        JSONParser jsonParser = new JSONParser();
        try{
            FileInputStream ip = new FileInputStream("config.properties");
            prop.load(ip);
            FileReader reader = new FileReader("users.json");
            Object obj = jsonParser.parse(reader);
            if(obj != null) {
                JSONArray users = (JSONArray) obj;

                for (int i = 0; i < users.size(); i++) {
                    JSONObject j = (JSONObject) users.get(i);
                    JSONObject user = (JSONObject) j.get("user");

                    userNames.add((String) user.get("userName"));
                    userIds.add((String) user.get("userId"));
                    roleIds.add((String) user.get("roleId"));
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void writeData(){
        JSONArray users = new JSONArray();

        for(int i = 0; i < userIds.size(); i++) {
            JSONObject idData = new JSONObject();
            idData.put("userName", userNames.get(i));
            idData.put("userId", userIds.get(i));
            idData.put("roleId", roleIds.get(i));

            JSONObject userData = new JSONObject();
            userData.put( "user", idData);

            users.add(userData);
        }
        //Write JSON file
        try {
            FileWriter file = new FileWriter("users.json");
            file.write(users.toJSONString());
            file.flush();

        } catch (IOException w) {
            w.printStackTrace();
        }
    }

}
