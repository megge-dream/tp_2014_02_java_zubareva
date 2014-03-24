package db;

public class UserDataSet {
    private String username;
    private String password;

    public UserDataSet(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}