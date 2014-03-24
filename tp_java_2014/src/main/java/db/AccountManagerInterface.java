package db;

import java.sql.SQLException;

/*
  Created by p.Kochetkov on 8.03.14.
*/
public interface AccountManagerInterface {

    void regUser(String login, String pass) throws SQLException;
    void logUser(String login, String pass) throws SQLException;

}
