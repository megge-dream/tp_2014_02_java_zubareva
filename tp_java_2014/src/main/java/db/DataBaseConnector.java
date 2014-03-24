package db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
//import com.mysql.jdbc.Driver;
/*
 Created by M.Zubareva
 */

public class DataBaseConnector {
        public static Connection getConnection() {
            try {
                Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
                DriverManager.registerDriver(driver);
                return DriverManager.getConnection("jdbc:mysql://" + "localhost:" + "3306/" +
                    "javadb?" + "user=root&" + "password=ьфч");
            }
            catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

}
