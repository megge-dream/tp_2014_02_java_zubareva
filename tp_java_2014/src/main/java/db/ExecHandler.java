package db;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
  Created by M.Zubareva
 */
public class ExecHandler {

    public ExecHandler() {}

    public UserDataSet handle(ResultSet res) throws SQLException {
        if (res.next()) {
            return new UserDataSet(res.getString("login"), res.getString("pass"));
        } else {
            return null;
        }
    }
}
