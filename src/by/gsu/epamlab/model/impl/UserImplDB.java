package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.ifaces.IUserDAO;
import by.gsu.epamlab.model.beans.Role;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.services.ConstantsDB;
import by.gsu.epamlab.model.services.DBManager;

import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class UserImplDB implements IUserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());

    @Override
    public User getUser(String name, String password) {
        Role role = Role.VISITOR;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getConnection();
            LOGGER.info(ConstantsDB.CONNECT_SUCCESS);
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
            DBManager.closeResultSets(resultSet);
            DBManager.closeStatements(statement);
            DBManager.closeConnection(connection);
        }
    }

    @Override
    public boolean addUser(String name, String password) {
        final String CREATE_TASKS =  "create table " + name + "_tasks (" +
                "  id int not null auto_increment," +
                "  description varchar(255) not null," +
                "  completionsDate datetime not null ," +
                "  isFixed bool not null default false," +
                "  isDelMarked bool not null default false," +
                "  fileName varchar(255)," +
                "primary key (id)" +
                ");";
        Connection connection = null;
        PreparedStatement statementUser = null;
        PreparedStatement statementId = null;
        PreparedStatement statementTasks = null;
        try {
            connection = DBManager.getConnection();
            statementUser = connection.prepareStatement(ConstantsSQL.ADD_USER);
            statementId = connection.prepareStatement(ConstantsSQL.USER_ID);
            statementTasks = connection.prepareStatement(CREATE_TASKS);
            statementUser.setString(ConstantsSQL.INDEX_ONE, name);
            statementUser.setString(ConstantsSQL.INDEX_TWO, password);
            synchronized (UserImplDB.class) {
                int id = getId(name, statementId);
                if (id == 0) {
                    statementUser.executeUpdate();
                    statementTasks.executeUpdate();
                }
                return id == 0;
            }
        } catch (DaoException | SQLException | IllegalArgumentException  | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            DBManager.closeStatements(statementUser, statementId, statementTasks);
            DBManager.closeConnection(connection);
        }
    }

    private int getId(String value, PreparedStatement statement) throws SQLException {
        int id = 0;
        ResultSet rs = null;
        try{
            statement.setString(ConstantsSQL.INDEX_ONE, value);
            rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(ConstantsSQL.INDEX_ONE);
            }
            return id;
        } finally {
            DBManager.closeResultSets(rs);
        }
    }


}
