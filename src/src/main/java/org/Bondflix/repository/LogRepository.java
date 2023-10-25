package org.Bondflix.repository;

import org.Bondflix.database.DatabaseManager;
import org.Bondflix.model.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LogRepository extends BaseRepository<Log>{
    private static LogRepository instance;

    protected LogRepository(Connection dbConnection, String tableName) {
        super(dbConnection, tableName);
    }

    public static LogRepository getInstance(){
        if (instance == null) {
            instance = new LogRepository(
                    DatabaseManager.getInstance().getDatabaseConnection(),
                    "log");
        }

        return instance;
    }
    @Override
    public Log create(Log log) throws SQLException {
        Statement stmt = this.dbConnection.createStatement();
        int rs = stmt.executeUpdate("INSERT INTO " + this.tableName + " (body_request, ip, endpoint, timestamp) VALUES ('" + log.getBodyRequest() + "', '" + log.getIP() + "', '" + log.getEndpoint() + "', '" + log.getTimestamp() + "')");
        if (rs > 0) {
            return log;
        }
        return null;
    }
}
