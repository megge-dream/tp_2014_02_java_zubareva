package db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

/*
Created by p.Kochetkov on 08.03.14.
 */
public class Executor {
    public static boolean execUpdate(Connection connection, String update) throws SQLException {
            Statement stmt = connection.createStatement();
            stmt.execute(update);
            int updated =  stmt.getUpdateCount();
            stmt.close();
            System.out.print(update);
            return updated > 0;
    }

    public static UserDataSet execQuery (Connection connection, String query,
                                  ExecHandler handler) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        UserDataSet user = handler.handle(result);
        result.close();
        stmt.close();
        return user;
    }
}
