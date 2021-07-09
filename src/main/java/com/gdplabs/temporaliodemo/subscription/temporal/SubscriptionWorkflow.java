package com.gdplabs.temporaliodemo.subscription.temporal;

import com.gdplabs.temporaliodemo.subscription.model.Customer;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SubscriptionWorkflow {

    String QUEUE_NAME = "Subscription";

    @WorkflowMethod
    void startWorkflow(Customer customer);

    @SignalMethod
    void cancelSubscription();

    @QueryMethod
    Customer getCustomer();

}
