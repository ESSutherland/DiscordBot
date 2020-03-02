package data;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;

public class Data {

    public static String PREFIX = "!";

    public static Properties prop = new Properties();

    public static Connection con;

    public static ArrayList<DBUser> userList;

    public static void connectDB(){
        try {
            FileInputStream ip = new FileInputStream("config.properties");
            prop.load(ip);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(Data.prop.getProperty("dbLink"), Data.prop.getProperty("dbUser"), Data.prop.getProperty("dbPass"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        try{
            String search = "select * from users";
            PreparedStatement ps = con.prepareStatement(search);
            ResultSet rs = ps.executeQuery();
            userList = new ArrayList<>();
            while(rs.next()){
                userList.add(new DBUser(rs.getString("userID"), rs.getString("userName"),
                        rs.getString("roleId"), rs.getString("colorHex"), rs.getInt("userLevel"), rs.getDouble("userExp")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addUserToDB(String userId, String userName) {
        try {
            connectDB();
            String update = "insert into users(userId, userName) values(?, ?)";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, userId);
            ps.setString(2, userName);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadData();
    }

    public static void updateUserInDB(String userId, String userName) {
        try {
            connectDB();
            String search = "select * from users where userId = ?";
            PreparedStatement ps = con.prepareStatement(search);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String update = "update users set userName = ? where userId = ?";
                ps = con.prepareStatement(update);
                ps.setString(1, userName);
                ps.setString(2, userId);
                ps.executeUpdate();
            } else {
                addUserToDB(userId, userName);
                updateUserInDB(userId, userName);
            }
            loadData();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateUserColorInDB(String userId, String userName, String roleId, String colorHex){
        try {
            connectDB();
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
            } else {
                addUserToDB(userId, userName);
                updateUserColorInDB(userId, userName, roleId, colorHex);
            }
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean findUserInDB(String userId) {
        try {
            connectDB();
            loadData();
            for(DBUser user : userList){
                if(user.getUserId().equalsIgnoreCase(userId)){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static DBUser getDBUser(String userId){
        for(DBUser user : userList){
            if(user.getUserId().equalsIgnoreCase(userId)){
                return user;
            }
        }
        return null;
    }

    public static void addExpToUser(String userId, double numExp){
        try {
            connectDB();
            String update = "update users set userExp = ? where userId = ?";
            PreparedStatement ps = con.prepareStatement(update);

            double pointsToAdd = getUserExp(userId) + numExp;

            ps.setDouble(1, pointsToAdd);
            ps.setString(2, userId);

            ps.executeUpdate();
            loadData();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setUserExp(String userId, double numExp){
        try {
            connectDB();
            String update = "update users set userExp = ? where userId = ?";
            PreparedStatement ps = con.prepareStatement(update);

            ps.setDouble(1, numExp);
            ps.setString(2, userId);

            ps.executeUpdate();
            loadData();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static double getUserExp(String userId){
        try{
            connectDB();
            loadData();
            String exp = "select userExp from users where userId = ?";
            PreparedStatement ps = con.prepareStatement(exp);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("userExp");
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
            connectDB();
            String update = "update users set userLevel = ? where userId = ?";
            PreparedStatement ps = con.prepareStatement(update);

            int newLevel = getUserLevel(userId) + 1;

            ps.setInt(1, newLevel);
            ps.setString(2, userId);

            ps.executeUpdate();
            loadData();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getUserLevel(String userId){
        try{
            connectDB();
            loadData();
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
            connectDB();
            loadData();
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

    public static int getUserRank(String userId){
        try{
            connectDB();
            loadData();
            int rank = 0;
            ArrayList<String> users = new ArrayList<String>();
            String level = "select * from users order by userLevel desc, userExp desc";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(level);
            while(rs.next()){
                users.add(rs.getString("userId"));
            }

            for(int i = 0; i < users.size(); i++){
                if(users.get(i).equalsIgnoreCase(userId)){
                    rank = i + 1;
                }
            }
            return rank;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void removeUserFromDB(String userId) {
        try {
            connectDB();
            String delete = "delete from users where userId = ?";
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setString(1, userId);
            ps.executeUpdate();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
