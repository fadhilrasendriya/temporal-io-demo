package com.gdplabs.temporaliodemo.subscription.temporal;

import com.gdplabs.temporaliodemo.subscription.model.Customer;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class SubscriptionWorkflowImpl implements SubscriptionWorkflow{

    private boolean subsctiptionCanceled;

    private final ActivityOptions activityOptions =
            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

    private final SubscriptionActivities activities = Workflow.newActivityStub(SubscriptionActivities.class, activityOptions);

    private Customer customer;

    @Override
    public void startWorkflow(Customer customer) {

        this.customer = customer;
        this.subsctiptionCanceled = false;
        this.activities.sendWelcomEmailToCustomer(customer);


        while(!subsctiptionCanceled) {
            this.activities.extendSubscription(customer);
            extendDuration();
            this.activities.chargeCustomer(customer);
            Workflow.await(customer.getSubscription().getBillingPeriod(), () -> subsctiptionCanceled);
        }
        
        activities.sendCancellationEmail(customer);
    }

    @Override
    public void cancelSubscription() {
        this.subsctiptionCanceled = true;
        this.activities.sendCancellationEmail(customer);
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    private void extendDuration() {
        customer.getSubscription().setBillingPeriodCharge(customer.getSubscription().getBillingPeriodCharge() + 10 );
        customer.getSubscription().setBillingPeriod(Duration.ofSeconds(10));
    }
}
