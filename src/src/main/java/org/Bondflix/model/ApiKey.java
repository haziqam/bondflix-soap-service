package org.Bondflix.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiKey extends Model{

    private String key;
    private String client;

    public ApiKey() {}

    public String getKey() {
        return key;
    }

    public String getClient() {
        return client;
    }

    @Override
    public void marshal(ResultSet rs) throws SQLException {
        this.key = rs.getString("api_key");
        this.client = rs.getString("client");
    }
}
