package com.gdplabs.temporaliodemo.subscription.config;


import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivitiesImpl;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionActivities;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionActivitiesImpl;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionWorkflow;
import com.gdplabs.temporaliodemo.subscription.temporal.SubscriptionWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SubscriptionConfig {


    @Bean
    public SubscriptionActivitiesImpl subscriptionActivities() {
        return new SubscriptionActivitiesImpl();
    }

    @Bean(name = "subscriptionWorker")
    @Autowired
    public Worker subscriptionWorker(WorkerFactory workerFactory, SubscriptionActivitiesImpl subscriptionActivities) {
        Worker subscriptionWorker = workerFactory.newWorker(SubscriptionWorkflow.QUEUE_NAME);
        subscriptionWorker.registerActivitiesImplementations(subscriptionActivities);
        subscriptionWorker.registerWorkflowImplementationTypes(SubscriptionWorkflowImpl.class);
        return subscriptionWorker;
    }


}
