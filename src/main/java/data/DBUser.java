package data;

public class DBUser {
    private String userName;
    private String userId;
    private String roleId;
    private String colorHex;
    private int userLevel;
    private double userExp;
    private String mcUsername;
    private int userPoints;

    public DBUser(String userId, String userName, String roleId, String colorHex, int userLevel, double userExp, String mcUsername/*, int userPoints*/){
        this.userId = userId;
        this.userName = userName;
        this.roleId = roleId;
        this.colorHex = colorHex;
        this.userLevel = userLevel;
        this.userExp = userExp;
        this.mcUsername = mcUsername;
        this.userPoints = userPoints;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId() { return userId; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() { return roleId; }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserExp(double userExp) {
        this.userExp = userExp;
    }

    public double getUserExp() {
        return userExp;
    }

    public void setMcUsername(String mcUsername){
        this.mcUsername = mcUsername;
    }

    public String getMcUsername() { return mcUsername; }

    public void setUserPoints(int userPoints) {this.userPoints = userPoints; }

    public int getUserPoints(){ return userPoints; }
}
