package db;

/*
  Created by p.Kochetkov on 09.03.14.
 */
public class SqlStringConstructor {
    public static String generateUpdate(UserDataSet user) {
        return "INSERT INTO users(login, pass) VALUES ('" + user.getUsername() + "','"
                + user.getPassword() + "');" ;
    }

    public static String generateSelect(String username) {
        return "SELECT * FROM users WHERE login = '" + username + "';";
    }
}
