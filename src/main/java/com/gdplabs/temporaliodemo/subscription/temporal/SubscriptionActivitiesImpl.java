package com.gdplabs.temporaliodemo.subscription.temporal;

import com.gdplabs.temporaliodemo.subscription.model.Customer;

import java.time.Duration;

public class SubscriptionActivitiesImpl implements SubscriptionActivities{

    @Override
    public void sendWelcomEmailToCustomer(Customer customer) {
        System.out.println("Email sent to " + customer.getId());
    }

    @Override
    public void sendCancellationEmail(Customer customer) {
        System.out.println("Cancellation has been sent to " + customer.getId());
    }

    @Override
    public void chargeCustomer(Customer customer) {
        System.out.println("Customer " + customer.getId() + " has been charged");
        customer.getSubscription().setBillingPeriodCharge(customer.getSubscription().getBillingPeriodCharge() + 10 );
    }

    @Override
    public void extendSubscription(Customer customer) {
        customer.getSubscription().setBillingPeriod(Duration.ofSeconds(10));
    }
}
