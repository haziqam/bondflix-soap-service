package org.Bondflix.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection databaseConnection;

    private static final int MAX_RETRY_ATTEMPTS = 6;
    private static final long RETRY_INTERVAL_MS = 10000;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private DatabaseManager() {
        int retryAttempts = 0;
        boolean connected = false;

        while (retryAttempts < MAX_RETRY_ATTEMPTS && !connected) {
            try {
                Dotenv dotenv = Dotenv.load();
                String dbURL;
                if (Objects.requireNonNull(dotenv.get("ENVIRONMENT")).equalsIgnoreCase("DEV")){
                    dbURL = dotenv.get("DB_URL_DEV");
                } else {
                    dbURL = dotenv.get("DB_URL_PROD");
                }
                String dbUser = dotenv.get("DB_USER");
                String dbPassword = dotenv.get("DB_PASS");
                assert dbURL != null;
                databaseConnection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                connected = true;
                System.out.println("Connected to db successfully");
            } catch (SQLException e) {
                System.out.println("Connection attempt failed: " + e.getMessage());
                retryAttempts++;

                if (retryAttempts < MAX_RETRY_ATTEMPTS) {
                    try {
                        Thread.sleep(RETRY_INTERVAL_MS);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        if (!connected) {
            System.err.println("Failed to establish a database connection after multiple retry attempts.");
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
