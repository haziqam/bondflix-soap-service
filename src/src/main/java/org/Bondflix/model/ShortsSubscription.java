package org.Bondflix.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortsSubscription extends Model {
    private Integer creatorId;
    private Integer subscriberId;
    private SubscriptionStatus subscriptionStatus;

    public ShortsSubscription() {
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    @Override
    public void marshal(ResultSet rs) throws SQLException {
        this.creatorId = rs.getInt("creator_id");
        this.subscriberId = rs.getInt("subscriber_id");
        this.subscriptionStatus = SubscriptionStatus.valueOf(rs.getString("status"));
    }

    public enum SubscriptionStatus {
        PENDING,
        REJECTED,
        ACCEPTED
    }
}
