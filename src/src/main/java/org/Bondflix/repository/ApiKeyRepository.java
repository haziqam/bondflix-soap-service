package org.Bondflix.repository;

import org.Bondflix.database.DatabaseManager;
import org.Bondflix.model.ApiKey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApiKeyRepository extends BaseRepository<ApiKey> {

    private static ApiKeyRepository instance;
    protected ApiKeyRepository(Connection dbConnection, String tableName) {
        super(dbConnection, tableName);
    }

    public static ApiKeyRepository getInstance(){
        if (instance == null) {
            instance = new ApiKeyRepository(
                    DatabaseManager.getInstance().getDatabaseConnection(),
                    "api_key");
        }

        return instance;
    }

    @Override
    public List<ApiKey> findAll() throws SQLException {
        List<ApiKey> result = new ArrayList<>();

        Statement stmt = DatabaseManager.getInstance().getDatabaseConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName);
        while (rs.next()) {
            ApiKey apiKey = new ApiKey();
            apiKey.marshal(rs);
            result.add(apiKey);
        }
        return result;
    }
}
