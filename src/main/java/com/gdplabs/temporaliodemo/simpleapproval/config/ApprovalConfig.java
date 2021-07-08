package com.gdplabs.temporaliodemo.simpleapproval.config;

import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivities;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivitiesImpl;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflow;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
@Configuration
public class ApprovalConfig {
    private String temporalServiceAddress = "127.0.0.1:7233";

    private String temporalNamespace = "default";

    @Autowired
    private ApplicationContext context;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs
                .newInstance(WorkflowServiceStubsOptions.newBuilder().setTarget(temporalServiceAddress).build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
        return WorkflowClient.newInstance(workflowServiceStubs,
                WorkflowClientOptions.newBuilder().setNamespace(temporalNamespace).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public ApprovalActivitiesImpl approvalActivities() {
        return new ApprovalActivitiesImpl();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initWorker() {
        WorkerFactory factory= context.getBean(WorkerFactory.class);
        ApprovalActivities activities = context.getBean(ApprovalActivities.class);
        Worker worker = factory.newWorker(ApprovalWorkflow.QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(ApprovalWorkflowImpl.class);
        worker.registerActivitiesImplementations(activities);
        factory.start();
    }


}
