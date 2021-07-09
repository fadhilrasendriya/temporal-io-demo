package com.gdplabs.temporaliodemo.subscription.service;

import com.gdplabs.temporaliodemo.subscription.model.Customer;
import com.gdplabs.temporaliodemo.subscription.model.Subscription;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionWorkflow;
import io.temporal.api.workflowservice.v1.TerminateWorkflowExecutionRequest;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SubscriptionService {

    @Autowired
    private WorkflowClient workflowClient;

    public void createCustomerAndSubscribe(String id, String name) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);

        Subscription subscription = new Subscription();
        subscription.setBillingPeriod(Duration.ofSeconds(10));

        customer.setSubscription(subscription);

        WorkflowOptions workflowOptions = WorkflowOptions
                .newBuilder()
                .setWorkflowId("Subscription_" + id)
                .setTaskQueue(SubscriptionWorkflow.QUEUE_NAME)
                .build();

        SubscriptionWorkflow workflow = workflowClient.newWorkflowStub(SubscriptionWorkflow.class, workflowOptions);
        WorkflowClient.start(workflow::startWorkflow, customer);


    }

    public void cancelSubscription(String id) {
        SubscriptionWorkflow workflow = workflowClient.newWorkflowStub(SubscriptionWorkflow.class, "Subscription_" + id);
        workflow.cancelSubscription();
    }

    public Customer getCustomer(String id) {
        SubscriptionWorkflow workflow = workflowClient.newWorkflowStub(SubscriptionWorkflow.class, "Subscription_" + id);
        return workflow.getCustomer();
    }



}
