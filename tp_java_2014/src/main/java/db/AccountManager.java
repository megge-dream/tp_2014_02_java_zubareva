package db;


import java.sql.SQLException;

/*
Created by p.Kochetkov on 08.03.14.
 */

public class AccountManager implements AccountManagerInterface {
    private UserDAO dao;

    public AccountManager() {
        dao = new UserDAO(DataBaseConnector.getConnection());
    }

    @Override
    public void regUser(String login, String pass) throws SQLException{
        isCorrectLogPas(login, pass);
        if (dao.findUser(login) != null) throw new SQLException();
        dao.addUser(new UserDataSet(login, pass));
    }

    @Override
    public void logUser(String login, String pass) throws SQLException {
        isCorrectLogPas(login, pass);
        UserDataSet user = dao.findUser(login);
        if ((user == null)||(!user.getPassword().equals(pass))) throw new SQLException();
    }

    private void isCorrectLogPas(String login, String pass) throws SQLException{
        if ((login == null)||(login.equals(""))||(pass.equals(""))||(pass == null))
            throw new SQLException();
    }

}
