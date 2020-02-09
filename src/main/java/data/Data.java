package data;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;

public class Data {

    public static String PREFIX = "!";

    public static String userID = "";
    public static String userNAME = "";
    public static String roleID = "";
    public static String colorHEX = "";

    public static Properties prop = new Properties();

    public static Connection con;

    public static void loadData() {
        try {
            FileInputStream ip = new FileInputStream("config.properties");
            prop.load(ip);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(Data.prop.getProperty("dbLink"), Data.prop.getProperty("dbUser"), Data.prop.getProperty("dbPass"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void addUserToDB(String userId, String userName) {
        try {
            String update = "insert into users(userID, userName) values(?, ?)";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, userId);
            ps.setString(2, userName);

            ps.executeUpdate();

            userID = userId;
            userNAME = userName;
            roleID = "";
            colorHEX = "";

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateUserColorInDB(String userId, String userName, String roleId, String colorHex){
        try {
            String search = "select * from users where userID = ?";
            PreparedStatement ps = con.prepareStatement(search);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String update = "update users set userName = ?, roleID = ?, colorHex = ? where userID = ?";
                ps = con.prepareStatement(update);
                ps.setString(1, userName);
                ps.setString(2, roleId);
                ps.setString(3, colorHex);
                ps.setString(4, userId);
                ps.executeUpdate();

                userID = userId;
                userNAME = userName;
                roleID = roleId;
                colorHEX = colorHex;
            } else {
                addUserToDB(userId, userName);
                updateUserColorInDB(userId, userName, roleId, colorHex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean findUserInDB(String userId) {
        try {
            String search = "select * from users where userID = ?";
            PreparedStatement ps = con.prepareStatement(search);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userID = rs.getString("userID");
                userNAME = rs.getString("userName");
                roleID = rs.getString("roleID");
                colorHEX = rs.getString("colorHex");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addExpToUser(String userId, int numExp){
        try {
            String update = "update users set userExp = ? where userId = ?";
            PreparedStatement ps = con.prepareStatement(update);

            int pointsToAdd = getUserExp(userId) + numExp;

            ps.setInt(1, pointsToAdd);
            ps.setString(2, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setUserExp(String userId, int numExp){
        try {
            String update = "update users set userExp = ? where userId = ?";
            PreparedStatement ps = con.prepareStatement(update);

            ps.setInt(1, numExp);
            ps.setString(2, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getUserExp(String userId){
        try{
            String exp = "select userExp from users where userId = ?";
            PreparedStatement ps = con.prepareStatement(exp);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("userExp");
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void levelUpUser(String userId){
        try {
            String update = "update users set userLevel = ? where userId = ?";
            PreparedStatement ps = con.prepareStatement(update);

            int newLevel = getUserLevel(userId) + 1;

            ps.setInt(1, newLevel);
            ps.setString(2, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getUserLevel(String userId){
        try{
            String level = "select userLevel from users where userId = ?";
            PreparedStatement ps = con.prepareStatement(level);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("userLevel");
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static ResultSet getTopLevels(){
        try{
            String level = "select * from users order by userLevel desc, userExp desc limit 0,5";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(level);
            return rs;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void removeUserFromDB(String userId) {
        try {
            String delete = "delete from users where userId = ?";
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
