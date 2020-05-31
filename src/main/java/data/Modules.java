package data;

import java.sql.*;
import java.util.ArrayList;

public class Modules {

    public static Connection con;

    public static void connectDB(){
        try {
            String url = "jdbc:sqlite:modules.db";
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void enableModule(int enable, String moduleName) throws SQLException {
        String update = "update modules set enabled = ? where name = ?";
        PreparedStatement ps = con.prepareStatement(update);
        if(enable == 0 || enable == 1){
            ps.setDouble(1, enable);
            ps.setString(2, moduleName);

            ps.executeUpdate();
        }
    }

    public static boolean isModuleEnabled(String moduleName) throws SQLException {
        String search = "select enabled from modules where name = ?";
        PreparedStatement ps = con.prepareStatement(search);
        ps.setString(1, moduleName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int result = rs.getInt("enabled");
            //System.out.println(moduleName + " - " + result);
            if (result == 0) {
                return false;
            } else if (result == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean findModule(String moduleName) throws SQLException {
        String search = "select * from modules where name = ?";
        PreparedStatement ps = con.prepareStatement(search);
        ps.setString(1, moduleName);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public static ArrayList<String[]> getModules() throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();

        String search = "select * from modules";
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(search);

        while(rs.next()){
            String[] arr = {rs.getString("name"), rs.getInt("enabled")+"", rs.getString("description")};
            results.add(arr);
        }

        return results;
    }
}
