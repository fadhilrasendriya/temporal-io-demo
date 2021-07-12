package com.gdplabs.temporaliodemo.subscription.temporal;

import com.gdplabs.temporaliodemo.subscription.model.Customer;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface SubscriptionActivities {

    @ActivityMethod
    void sendWelcomEmailToCustomer(Customer customer);

    @ActivityMethod
    void sendCancellationEmail(Customer customer);

    @ActivityMethod
    void chargeCustomer(Customer customer);

    @ActivityMethod
    void extendSubscription(Customer customer);


}
