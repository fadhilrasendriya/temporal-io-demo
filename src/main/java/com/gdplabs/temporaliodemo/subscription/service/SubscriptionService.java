package com.gdplabs.temporaliodemo.subscription.service;

import com.gdplabs.temporaliodemo.subscription.model.Customer;
import com.gdplabs.temporaliodemo.subscription.model.Subscription;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private WorkflowClient workflowClient;

    public void createCustomerAndSubscribe(String id, String name) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);

        Subscription subscription = new Subscription();

        customer.setSubscription(subscription);

        WorkflowOptions workflowOptions = WorkflowOptions
                .newBuilder()
                .setWorkflowId("Subscription_" + id)
                .setTaskQueue(SubscriptionWorkflow.QUEUE_NAME).build();

        SubscriptionWorkflow workflow = workflowClient.newWorkflowStub(SubscriptionWorkflow.class, workflowOptions);
        WorkflowClient.start(workflow::startWorkflow, customer);


    }

    public void cancelSubscription(String id) {

    }


}
