package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.services.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseImplDB {
    private static final Logger LOGGER = Logger.getLogger(FileImplDB.class.getName());

    protected void proccesTask(String sql, int... ids) {
        Connection connection = null;
        PreparedStatement statement = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(sql);
            for (int id : ids ) {
                statement.setInt(ConstantsSQL.INDEX_ONE, id);
                statement.executeUpdate();
            }

        } catch (DaoException | SQLException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            pool.closeStatements(statement);
            pool.returnConnection(connection);
        }
    }

    protected void actionTask(String userName, Task task, String sql) {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementId = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            statementId = connection.prepareStatement(ConstantsSQL.USER_ID);
            int userId = getId(userName, statementId);
            statement = connection.prepareStatement(sql);
            statement.setString(ConstantsSQL.INDEX_ONE, task.getDescription());
            statement.setDate(ConstantsSQL.INDEX_TWO, task.getCompletionsDate());
            statement.setInt(ConstantsSQL.INDEX_THREE, userId);

            statement.executeUpdate();

        } catch (DaoException | SQLException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            pool.closeStatements(statement, statementId);
            pool.returnConnection(connection);
        }
    }

    protected int getId(String value, PreparedStatement statement) throws SQLException {
        int id = 0;
        ResultSet rs = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try{
            statement.setString(ConstantsSQL.INDEX_ONE, value);
            rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(ConstantsSQL.INDEX_ONE);
            }
            return id;
        } finally {
            pool.closeResultSets(rs);
        }
    }
}
