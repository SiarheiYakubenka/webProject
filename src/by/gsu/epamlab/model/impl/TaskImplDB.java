package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.services.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskImplDB extends BaseImplDB implements ITaskDAO {
    private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());

    @Override
    public List<Task> getList(String userName) {
        return getList(userName, ConstantsSQL.KEY_ID);
    }

    public List<Task> getList(String userName, String condition) {
        final String SQL = ConstantsSQL.SELECT_TASKS_HEADER + condition;
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statementTasks = null;
        PreparedStatement statementId = null;
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            statementId = connection.prepareStatement(ConstantsSQL.USER_ID);
            statementTasks = connection.prepareStatement(SQL);
            int userId = getId(userName, statementId);
            statementTasks = connection.prepareStatement(SQL);
            statementTasks.setInt(ConstantsSQL.INDEX_ONE, userId);
            resultSet = statementTasks.executeQuery();
            int id;
            String description, fileName;
            Date completionsDate;
            boolean isFixed, isDelMarked;
            while (resultSet.next()) {
                id = resultSet.getInt(ConstantsSQL.KEY_ID);
                description = resultSet.getString(ConstantsSQL.KEY_DESCRIPTION);
                completionsDate = resultSet.getDate(ConstantsSQL.KEY_DATE);
                isFixed = resultSet.getBoolean(ConstantsSQL.KEY_IS_FIXED);
                isDelMarked = resultSet.getBoolean(ConstantsSQL.KEY_IS_DEL);
                fileName = resultSet.getString(ConstantsSQL.KEY_FILENAME);
                tasks.add(new Task(id, description, completionsDate, isFixed, isDelMarked, fileName));
            }
            return tasks;
        } catch (DaoException | SQLException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        } finally {
            pool.closeResultSets(resultSet);
            pool.closeStatements(statementTasks, statementId);
            pool.returnConnection(connection);
        }
    }

    @Override
    public void add(String userName, Task task) {
        actionTask(userName, task, ConstantsSQL.ADD_TASK);
    }

    @Override
    public void edit(String userName, Task task) {
        final String SQL = ConstantsSQL.UPDATE_TASK + task.getId();
        actionTask(userName, task, SQL);
    }

    @Override
    public void delete(int... ids) {
        proccesTask(ConstantsSQL.DELETE_TASK, ids);
    }

    @Override
    public void fix(int... ids) {
        proccesTask(ConstantsSQL.FIX_TASK, ids);
    }

    @Override
    public void recover(int... ids) {
        proccesTask(ConstantsSQL.RECOVER_TASK, ids);
    }

    @Override
    public void remove(int... ids) {
        proccesTask(ConstantsSQL.REMOVE_TASK, ids);
    }
}
