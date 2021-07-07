package com.gdplabs.temporaliodemo;

import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalActivities;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflow;
import com.gdplabs.temporaliodemo.simpleapproval.temporal.ApprovalWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TemporalIoDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(TemporalIoDemoApplication.class, args);
        WorkerFactory factory= appContext.getBean(WorkerFactory.class);
        ApprovalActivities activities = appContext.getBean(ApprovalActivities.class);
        Worker worker = factory.newWorker(ApprovalWorkflow.QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(ApprovalWorkflowImpl.class);
        worker.registerActivitiesImplementations(activities);
        factory.start();
    }

}
