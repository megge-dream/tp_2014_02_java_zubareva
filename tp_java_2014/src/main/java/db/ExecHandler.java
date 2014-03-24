package db;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
  Created by p.Kochetkov on 08.03.14.
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
