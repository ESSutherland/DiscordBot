package data;

import java.sql.*;
import java.util.ArrayList;

public class AnimalCrossingAPI {
    public static ArrayList<ACVillager> villagers = new ArrayList<>();

    private static String url = "http://acnhapi.com/v1/images/villagers/";
    private static String url2 = "http://acnhapi.com/v1/icons/villagers/";

    public static Connection con;

    public static void connect() {
        try {
            String url = "jdbc:sqlite:villagers.db";
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadData();
    }

    public static void loadData() {
        try{
            String search = "select * from villagers";
            PreparedStatement ps = con.prepareStatement(search);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ACVillager villager = new ACVillager();
                villager.setId(rs.getInt("id"));
                villager.setFileName(rs.getString("filename"));
                villager.setName(rs.getString("name"));
                villager.setPersonality(rs.getString("personality"));
                villager.setBirthday(rs.getString("birthday"));
                villager.setSpecies(rs.getString("species"));
                villager.setGender(rs.getString("gender"));
                villager.setCatchphrase(rs.getString("catchphrase"));
                villager.setIcon(rs.getString("icon"));

                villagers.add(villager);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
