package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.services.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseImplDB {
    private static final Logger LOGGER = Logger.getLogger(FileImplDB.class.getName());

    protected void proccesTask(String sql, int... ids) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            for (int id : ids ) {
                statement.setInt(ConstantsSQL.INDEX_ONE, id);
                statement.executeUpdate();
            }

        } catch (DaoException | SQLException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            DBManager.closeStatements(statement);
            DBManager.closeConnection(connection);
        }
    }

    protected void actionTask(Task task, String sql) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(ConstantsSQL.INDEX_ONE, task.getDescription());
            statement.setDate(ConstantsSQL.INDEX_TWO, task.getCompletionsDate());

            statement.executeUpdate();


        } catch (DaoException | SQLException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            DBManager.closeStatements(statement);
            DBManager.closeConnection(connection);
        }
    }
}
