package db;

import java.sql.Connection;
import java.sql.SQLException;

/*
 Created by M.Zubareva
 */

public class UserDAO {

    ExecHandler handler;
    Connection connection;

    UserDAO (Connection connect) {
        connection = connect;
        handler = new ExecHandler();
    }

    UserDataSet findUser(String username) throws SQLException {
        return Executor.execQuery(connection, SqlStringConstructor.generateSelect(username),
                handler);
    }

    boolean addUser(UserDataSet user) throws SQLException {
        return Executor.execUpdate(connection, SqlStringConstructor.generateUpdate(user));
    }
}
