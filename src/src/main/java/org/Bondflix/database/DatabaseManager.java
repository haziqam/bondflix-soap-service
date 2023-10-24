package org.Bondflix.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection databaseConnection;

    public static void getInstance(String dbURL, String dbUser, String dbPassword) {
        if (instance == null) {
            instance = new DatabaseManager(dbURL, dbUser, dbPassword);
        }
    }

    private DatabaseManager(String dbURL, String dbUser, String dbPassword) {
        try {
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
