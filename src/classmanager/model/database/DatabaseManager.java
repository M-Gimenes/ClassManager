package classmanager.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import classmanager.util.LoggerUtil;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;

    private static final String DB_URL = "jdbc:sqlite:school.db";

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

        } catch (SQLException e) {
            LoggerUtil.logError("DatabaseManager - creation", e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
