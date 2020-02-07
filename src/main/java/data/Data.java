package data;

import java.io.*;
import java.util.Properties;
import java.sql.*;

public class Data {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(Data.prop.getProperty("dbLink"), Data.prop.getProperty("dbUser"), Data.prop.getProperty("dbPass"));
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from users");

            while (rs.next()) {
                System.out.println(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void addUserToDB(String userId, String name, String roleId, String colorHex) {
        try {
            String update = "insert into users(userID, userName, roleID, colorHex) values(?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, roleId);
            ps.setString(4, colorHex);
            ps.executeUpdate();

            userID = userId;
            userNAME = name;
            roleID = roleId;
            colorHEX = colorHex;

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateUserDB(String userId, String name, String roleId, String colorHex) {
        try {
            String search = "select * from users where userID = ?";
            PreparedStatement ps = con.prepareStatement(search);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String update = "update users set userName = ?, roleID = ?, colorHex = ? where userID = ?";
                ps = con.prepareStatement(update);
                ps.setString(1, name);
                ps.setString(2, roleId);
                ps.setString(3, colorHex);
                ps.setString(4, userId);
                ps.executeUpdate();

                userID = userId;
                userNAME = name;
                roleID = roleId;
                colorHEX = colorHex;
            } else {
                addUserToDB(userId, name, roleId, colorHex);
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
