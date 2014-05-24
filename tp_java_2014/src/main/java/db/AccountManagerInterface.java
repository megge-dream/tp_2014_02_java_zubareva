package db;

import java.sql.SQLException;

/*
   Created by M.Zubareva
*/
public interface AccountManagerInterface {

    int regUser(String login, String pass) throws SQLException;
    int logUser(String login, String pass) throws SQLException;

}
