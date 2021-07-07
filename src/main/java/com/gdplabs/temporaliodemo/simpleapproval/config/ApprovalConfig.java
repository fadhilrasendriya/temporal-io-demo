package com.gdplabs.temporaliodemo.simpleapproval.config;

import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivities;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivitiesImpl;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Configuration
public class ApprovalConfig {
    private String temporalServiceAddress = "127.0.0.1:7233";

    private String temporalNamespace = "default";


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


}
