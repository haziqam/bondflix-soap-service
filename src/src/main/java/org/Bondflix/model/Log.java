package org.Bondflix.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Log extends Model{
    private String bodyRequest;
    private String IP;
    private String endpoint;
    private String timestamp;
    private String client;

    public Log(String bodyRequest, String IP, String endpoint, String timestamp, String client) {
        this.bodyRequest = bodyRequest;
        this.IP = IP;
        this.endpoint = endpoint;
        this.timestamp = timestamp;
        this.client = client;
    }

    public String getBodyRequest() {
        return bodyRequest;
    }

    public void setBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public void createFromSQL(ResultSet rs) throws SQLException {
        this.client = rs.getString("client");
        this.bodyRequest = rs.getString("body_request");
        this.IP = rs.getString("ip");
        this.endpoint = rs.getString("endpoint");
        this.timestamp = rs.getTimestamp("timestamp").toString();
    }
}
