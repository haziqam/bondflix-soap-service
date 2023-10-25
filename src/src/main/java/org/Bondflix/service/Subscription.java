package org.Bondflix.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;
import org.Bondflix.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebService
public class Subscription extends Service {

    @WebMethod
    public double add(@WebParam(name="arg1") double a, @WebParam(name = "arg2") double b) throws Exception {
        this.validateAndRecord(a, b);
        return a+b;
    }

    @WebMethod
    public boolean isUserSubscribed(@WebParam(name = "userId") int userId) {
        try {
            Connection dbConn = Objects.requireNonNull(DatabaseManager.getInstance()).getDatabaseConnection();
            PreparedStatement statement = dbConn.prepareStatement(
                    "SELECT isSubscribed FROM UserSubscription WHERE userId = ?"
            );
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("isSubscribed");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @WebMethod
    public List<Integer> getSubscribedUsers() {
        Connection dbConn = Objects.requireNonNull(DatabaseManager.getInstance()).getDatabaseConnection();
        List<Integer> subscribedUsers = new ArrayList<>();
        try (PreparedStatement statement = dbConn.prepareStatement("SELECT userId FROM UserSubscription WHERE isSubscribed = true");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                subscribedUsers.add(resultSet.getInt("userId"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subscribedUsers;
    }

    public boolean addSubscribedUser(@WebParam(name = "userId") int userId) {
        Connection dbConn = Objects.requireNonNull(DatabaseManager.getInstance()).getDatabaseConnection();
        try (PreparedStatement insertStatement = dbConn.prepareStatement("INSERT INTO UserSubscription (userId, isSubscribed) VALUES (?, true) ON DUPLICATE KEY UPDATE isSubscribed = true")) {
            insertStatement.setInt(1, userId);
            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSubscriptionStatus(@WebParam(name = "userId") int userId, @WebParam(name = "subscriptionStatus") boolean isSubscribed) {
        Connection dbConn = Objects.requireNonNull(DatabaseManager.getInstance()).getDatabaseConnection();
        try (PreparedStatement updateStatement = dbConn.prepareStatement("UPDATE UserSubscription SET isSubscribed = ? WHERE userId = ?")) {
            updateStatement.setBoolean(1, isSubscribed);
            updateStatement.setInt(2, userId);
            int rowsAffected = updateStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
