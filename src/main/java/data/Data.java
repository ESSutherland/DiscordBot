package data;

import java.io.*;
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
