package by.gsu.epamlab.model.services;

import by.gsu.epamlab.model.impl.DaoException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class.getName());
    static {
        try {
            Class.forName(ConstantsDB.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DaoException(ConstantsDB.ERROR_LOAD, e);
        }
    }

    public static Connection getConnection() {
        try {
            LOGGER.info(ConstantsDB.CONNECTION);
            return DriverManager.getConnection(ConstantsDB.DB_URL, ConstantsDB.DB_LOGIN, ConstantsDB.DB_PASSWORD);
        } catch (SQLException e) {
            throw new DaoException(ConstantsDB.ERROR_LOAD, e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                LOGGER.info(ConstantsDB.CONNECTION_CLOSED);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, ConstantsDB.ERROR_CONNECTION_CLOSING_PROBLEM , e);
        }

    }

    public static void closeStatements(Statement...statements) {
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

    public static void closeResultSets(ResultSet...resultSets){
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
