package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.ifaces.IUserDAO;
import by.gsu.epamlab.model.beans.Role;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.services.ConnectionPool;

import java.sql.*;
import java.util.logging.*;

public class UserImplDB extends BaseImplDB implements IUserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());

    @Override
    public User getUser(String name, String password) {
        Role role = Role.VISITOR;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(ConstantsSQL.SELECT_USER);
            statement.setString(ConstantsSQL.INDEX_ONE, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String passwordUser = resultSet.getString(ConstantsSQL.PARAM_PASS);
                String roleStr =  resultSet.getString(ConstantsSQL.PARAM_ROLE);
                role = Role.getRole(password, passwordUser, roleStr);
            }
            return new User(name, role);
        } catch (DaoException | SQLException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return null;
        } finally {
            pool.closeResultSets(resultSet);
            pool.closeStatements(statement);
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean addUser(String name, String password) {
        Connection connection = null;
        PreparedStatement statementUser = null;
        PreparedStatement statementId = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            statementUser = connection.prepareStatement(ConstantsSQL.ADD_USER);
            statementId = connection.prepareStatement(ConstantsSQL.USER_ID);
            statementUser.setString(ConstantsSQL.INDEX_ONE, name);
            statementUser.setString(ConstantsSQL.INDEX_TWO, password);
            synchronized (UserImplDB.class) {
                int id = getId(name, statementId);
                if (id == 0) {
                    statementUser.executeUpdate();
                }
                return id == 0;
            }
        } catch (DaoException | SQLException | IllegalArgumentException  | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            pool.closeStatements(statementUser, statementId);
            pool.returnConnection(connection);
        }
    }
}
