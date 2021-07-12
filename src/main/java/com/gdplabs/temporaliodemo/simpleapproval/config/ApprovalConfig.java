package com.gdplabs.temporaliodemo.simpleapproval.config;

import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivitiesImpl;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflow;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class ApprovalConfig {

    @Bean
    public ApprovalActivitiesImpl approvalActivities() {
        return new ApprovalActivitiesImpl();
    }

    @Bean(name = "approvalWorker")
    @Autowired
    public Worker approvalWorker(WorkerFactory workerFactory, ApprovalActivitiesImpl approvalActivities) {
        Worker approvalWorker = workerFactory.newWorker(ApprovalWorkflow.QUEUE_NAME);
        approvalWorker.registerWorkflowImplementationTypes(ApprovalWorkflowImpl.class);
        approvalWorker.registerActivitiesImplementations(approvalActivities);
        return approvalWorker;
    }
}
