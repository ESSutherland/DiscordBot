package data;

import java.sql.*;
import java.util.ArrayList;

public class CommandData {

    public static Connection con;

    public static void connectDB(){
        try {
            String url = "jdbc:sqlite:commands.db";
            String sql = "CREATE TABLE IF NOT EXISTS commands (\n"
                    + "    command text PRIMARY KEY,\n"
                    + "    message text\n"
                    + ");";
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            Statement s = con.createStatement();
            s.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCommand(String command, String message) throws SQLException{
        String update = "insert into commands(command, message) values(?, ?)";
        PreparedStatement ps = con.prepareStatement(update);
        ps.setString(1, command);
        ps.setString(2, message);
        ps.executeUpdate();
    }

    public static boolean isCommand(String command) throws SQLException {
        try {
            String search = "select * from commands where command = ?";
            PreparedStatement ps = con.prepareStatement(search);
            ps.setString(1, command);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        }
        catch(NullPointerException ex){
            System.out.println("Command not found");
            return false;
        }
    }

    public static String getMessage(String command) throws SQLException {
        String search = "select message from commands where command = ?";
        PreparedStatement ps = con.prepareStatement(search);
        ps.setString(1, command);
        ResultSet rs = ps.executeQuery();
        String message = null;
        while(rs.next()) {
             message = rs.getString("message");
        }
        return message;
    }

    public static ArrayList<String[]> getCommands() throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();

        String search = "select * from commands";
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(search);

        while(rs.next()){
            String[] arr = {rs.getString("command"), rs.getString("message")};
            results.add(arr);
        }
        return results;
    }

    public static void removeCommand(String command) throws SQLException {
        String delete = "delete from commands where command = ?";
        PreparedStatement ps = con.prepareStatement(delete);
        ps.setString(1, command);
        ps.executeUpdate();
    }
}