package org.Bondflix.repository;

import org.Bondflix.database.DatabaseManager;
import org.Bondflix.model.ShortsSubscription;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShortsSubscriptionRepository extends BaseRepository<ShortsSubscription> {
    private static ShortsSubscriptionRepository instance;
    protected ShortsSubscriptionRepository(Connection dbConnection, String tableName) {
        super(dbConnection, tableName);
    }

    public static ShortsSubscriptionRepository getInstance(){
        if (instance == null){
            return new ShortsSubscriptionRepository(
                    DatabaseManager.getInstance().getDatabaseConnection(),
                    "shorts_subscription"
            );
        }
        return instance;
    }

    @Override
    public ShortsSubscription create(ShortsSubscription sub) throws SQLException {
        Statement stmt = this.dbConnection.createStatement();
        int rs = stmt.executeUpdate("INSERT INTO " + this.tableName + " (creator_id, subscriber_id, status) VALUES ('" + sub.getCreatorId() + "', '" + sub.getSubscriberId() + "', '" + sub.getSubscriptionStatus() + "')");
        if (rs > 0) {
            return sub;
        }
        return null;
    };

    public ShortsSubscription update(ShortsSubscription sub) throws SQLException {
        Statement stmt = dbConnection.createStatement();
        int rs = stmt.executeUpdate("UPDATE " + tableName + " SET status = '" + sub.getSubscriptionStatus() + "' WHERE creator_id = " + sub.getCreatorId() + " AND subscriber_id = " + sub.getSubscriberId());
        if (rs > 0) {
            return sub;
        }
        return null;
    }

    @Override
    public ShortsSubscription delete(ShortsSubscription sub) throws SQLException {
        Statement stmt = dbConnection.createStatement();
        int rs = stmt.executeUpdate("DELETE FROM " + tableName + " WHERE creator_id = " + sub.getCreatorId() + " AND subscriber_id = " + sub.getSubscriberId());
        if (rs > 0) {
            return sub;
        }
        return null;
    }

    @Override
    public List<ShortsSubscription> findAll() throws SQLException {
        List<ShortsSubscription> subscriptions = new ArrayList<>();
        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

        while (rs.next()) {
            ShortsSubscription sub = new ShortsSubscription();
            sub.marshal(rs);
            subscriptions.add(sub);
        }

        return subscriptions;
    }

    public List<ShortsSubscription> subscriberList(Integer creatorId) throws SQLException {
        List<ShortsSubscription> subscriptions = new ArrayList<>();
        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE creator_id = " + creatorId);

        while (rs.next()) {
            ShortsSubscription sub = new ShortsSubscription();
            sub.marshal(rs);
            subscriptions.add(sub);
        }

        return subscriptions;
    }

    public List<ShortsSubscription> subscribedToList(Integer subscriberId) throws SQLException {
        List<ShortsSubscription> subscriptions = new ArrayList<>();
        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE subscriber_id = " + subscriberId);

        while (rs.next()) {
            ShortsSubscription sub = new ShortsSubscription();
            sub.marshal(rs);
            subscriptions.add(sub);
        }

        return subscriptions;
    }
}
