package org.Bondflix.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection databaseConnection;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private DatabaseManager() {
        try {
            Dotenv dotenv = Dotenv.load();
            String dbURL = dotenv.get("DB_URL_DEV");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASS");
            assert dbURL != null;
            databaseConnection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getDatabaseConnection() {
        return databaseConnection;
    }

    public void closeConnection() {
        if (databaseConnection != null) {
            try {
                databaseConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
