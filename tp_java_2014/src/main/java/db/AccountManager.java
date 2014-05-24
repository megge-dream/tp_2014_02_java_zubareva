package db;


import java.sql.SQLException;

/*
Created by M.Zubareva
 */

public class AccountManager implements AccountManagerInterface {

    public AccountManager() {
    }

    @Override
    public int regUser(String login, String pass) throws SQLException{
        UserDAO dao = new UserDAO(DataBaseConnector.getConnection());
        if (isCorrectLogPas(login, pass) != 0)
            return -1;
        if (dao.findUser(login) != null)
            return -1;
        dao.addUser(new UserDataSet(login, pass));
        return 0;
    }

    @Override
    public int logUser(String login, String pass) throws SQLException {
        UserDAO dao = new UserDAO(DataBaseConnector.getConnection());
        if (isCorrectLogPas(login, pass) != 0)
            return -1;
        UserDataSet user = dao.findUser(login);
        if ((user == null)||(!user.getPassword().equals(pass)))
            return -1;
        return 0;
    }

    private int isCorrectLogPas(String login, String pass) throws SQLException{
        if ((login == null)||(login.isEmpty())||(pass.isEmpty())||(pass == null))
            return -1;
        return 0;
    }

}
