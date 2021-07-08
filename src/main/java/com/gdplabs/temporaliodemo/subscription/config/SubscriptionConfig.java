package com.gdplabs.temporaliodemo.subscription.config;

import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivities;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivitiesImpl;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflow;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflowImpl;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionActivities;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionActivitiesImpl;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionWorkflow;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SubscriptionConfig {


    @Bean
    public SubscriptionActivities subscriptionActivities() {
        return new SubscriptionActivitiesImpl();
    }


}
