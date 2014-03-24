package db;

import java.sql.SQLException;

/*
   Created by M.Zubareva
*/
public interface AccountManagerInterface {

    void regUser(String login, String pass) throws SQLException;
    void logUser(String login, String pass) throws SQLException;

}
