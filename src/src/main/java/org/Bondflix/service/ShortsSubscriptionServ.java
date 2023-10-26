package org.Bondflix.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.Bondflix.model.ShortsSubscription;
import org.Bondflix.repository.ShortsSubscriptionRepository;
import java.util.ArrayList;
import java.util.List;

@WebService
public class ShortsSubscriptionServ extends Service {

    @WebMethod
    public double add(@WebParam(name="arg1") double a, @WebParam(name = "arg2") double b) throws Exception {
        this.log(a, b);
        return a+b;
    }

    @WebMethod
    public boolean isUserSubscribedToCreator(@WebParam(name = "userId") int userId, @WebParam(name = "creatorId") int creatorId) throws Exception {
        this.log(userId, creatorId);
        try {
            ShortsSubscriptionRepository repository = ShortsSubscriptionRepository.getInstance();
            List<ShortsSubscription> subscriptions = repository.subscriberList(creatorId);

            for (ShortsSubscription subscription : subscriptions) {
                if (subscription.getSubscriberId() == userId) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @WebMethod
    public List<Integer> getSubscribedUsers() throws Exception {
        this.log();
        try {
            ShortsSubscriptionRepository repository = ShortsSubscriptionRepository.getInstance();
            List<ShortsSubscription> subscriptions = repository.findAll();

            List<Integer> subscribedUsers = new ArrayList<>();

            for (ShortsSubscription subscription : subscriptions) {
                subscribedUsers.add(subscription.getSubscriberId());
            }

            return subscribedUsers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @WebMethod
    public boolean updateSubscriptionByCreatorId(@WebParam(name = "creatorId") int creatorId, @WebParam(name = "subscriptionStatus") boolean isSubscribed) throws Exception {
        this.log(creatorId, isSubscribed);
        try {
            ShortsSubscriptionRepository repository = ShortsSubscriptionRepository.getInstance();
            List<ShortsSubscription> subscriptions = repository.subscriberList(creatorId);

            for (ShortsSubscription subscription : subscriptions) {
                subscription.setSubscriptionStatus(isSubscribed ? ShortsSubscription.SubscriptionStatus.ACCEPTED : ShortsSubscription.SubscriptionStatus.REJECTED);
                repository.update(subscription);
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @WebMethod
    public boolean addCreatorSubscriberRelationship(@WebParam(name = "creatorId") int creatorId, @WebParam(name = "subscriberId") int subscriberId) throws Exception {
        this.log(creatorId, subscriberId);
        try {
            ShortsSubscriptionRepository repository = ShortsSubscriptionRepository.getInstance();
            ShortsSubscription subscription = new ShortsSubscription();
            subscription.setCreatorId(creatorId);
            subscription.setSubscriberId(subscriberId);
            subscription.setSubscriptionStatus(ShortsSubscription.SubscriptionStatus.PENDING);

            ShortsSubscription createdSubscription = repository.create(subscription);

            return createdSubscription != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @WebMethod
    public List<Integer> getSubscribersByCreatorId(@WebParam(name = "creatorId") int creatorId) throws Exception {
        this.log(creatorId);
        try {
            ShortsSubscriptionRepository repository = ShortsSubscriptionRepository.getInstance();
            List<ShortsSubscription> subscriptions = repository.subscriberList(creatorId);

            List<Integer> subscribers = new ArrayList<>();

            for (ShortsSubscription subscription : subscriptions) {
                subscribers.add(subscription.getSubscriberId());
            }

            return subscribers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @WebMethod
    public List<Integer> getCreatorsBySubscriberId(@WebParam(name = "subscriberId") int subscriberId) throws Exception {
        this.log(subscriberId);
        try {
            ShortsSubscriptionRepository repository = ShortsSubscriptionRepository.getInstance();
            List<ShortsSubscription> subscriptions = repository.subscribedToList(subscriberId);

            List<Integer> creators = new ArrayList<>();

            for (ShortsSubscription subscription : subscriptions) {
                creators.add(subscription.getCreatorId());
            }

            return creators;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }


}
