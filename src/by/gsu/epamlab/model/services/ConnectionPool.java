package by.gsu.epamlab.model.services;

import by.gsu.epamlab.controllers.Constants;
import by.gsu.epamlab.model.impl.DaoException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());
    private ConnectionPool(){
    }

    private static ConnectionPool instance = null;
    private static DataSource dataSource = null;

    public static ConnectionPool getInstance(){
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public static DataSource getDataSource() throws NamingException {
        if (dataSource == null) {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup(Constants.POOL_NAME);
        }
        return dataSource;
    }

    public Connection getConnection(){
        Connection c = null;
        try {
            c = getDataSource().getConnection();
            return c;
        } catch (NamingException | SQLException e) {
            throw new DaoException(ConstantsDB.ERROR_LOAD, e);
        }
    }

    public  void returnConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, ConstantsDB.ERROR_CONNECTION_CLOSING_PROBLEM , e);
        }
    }

    public void closeStatements(Statement...statements) {
        try {
            if (statements.length != 0) {
                for (Statement st: statements) {
                    if (st != null) {
                        st.close();
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, ConstantsDB.ERROR_STATEMENTS_CLOSING_PROBLEM , e);

        }

    }

    public void closeResultSets(ResultSet...resultSets){
        try {
            if (resultSets.length != 0) {
                for (ResultSet rs: resultSets) {
                    if (rs != null) {
                        rs.close();
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, ConstantsDB.ERROR_RESULT_SET_CLOSING_PROBLEM , e);
        }

    }
}
