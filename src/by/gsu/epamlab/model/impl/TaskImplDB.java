package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.services.ConstantsDB;
import by.gsu.epamlab.model.services.DBManager;

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
        final String SQL = "select * from " + userName + "_tasks where " + condition;
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getConnection();
            LOGGER.info(ConstantsDB.CONNECT_SUCCESS);
            statement = connection.prepareStatement(SQL);
            resultSet = statement.executeQuery();
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
            return null;
        } finally {
            DBManager.closeResultSets(resultSet);
            DBManager.closeStatements(statement);
            DBManager.closeConnection(connection);
        }
    }

    @Override
    public void add(String userName, Task task) {
        final String ADD_TASK = "insert into " + userName + "_tasks (description, completionsDate)" +
            " values (?, ?);";
        actionTask(task, ADD_TASK);
    }

    @Override
    public void edit(String userName, Task task) {
        final String SQL = "update " + userName + "_tasks set description=?, completionsDate=? where id=" + task.getId();
        actionTask(task, SQL);
    }

    @Override
    public void delete(String userName, int... ids) {
        final String SQL = "update " + userName + "_tasks set isDelMarked=true where id=?";
        proccesTask(SQL, ids);
    }

    @Override
    public void fix(String userName, int... ids) {
        final String SQL = "update " + userName + "_tasks set isFixed=true where id=?";
        proccesTask(SQL, ids);
    }

    @Override
    public void recover(String userName, int... ids) {
        final String SQL = "update " + userName + "_tasks set isDelMarked=false, isFixed=false where id=?";
        proccesTask(SQL, ids);
    }

    @Override
    public void remove(String userName, int... ids) {
        final String SQL = "delete from " + userName + "_tasks where id=?";
        proccesTask(SQL, ids);
    }
}
