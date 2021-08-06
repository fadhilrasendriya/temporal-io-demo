package com.gdplabs.temporaliodemo.workermanager;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class WorkerManager {


    @Value("${temporalServiceHost}")
    private String temporalServiceHost;

    @Value("${temporalServicePort}")
    private String temporalServicePort;


    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs
                .newInstance(WorkflowServiceStubsOptions.newBuilder().setTarget(temporalServiceHost + ":" + temporalServicePort).build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
        String temporalNamespace = "default";
        return WorkflowClient.newInstance(workflowServiceStubs,
                WorkflowClientOptions.newBuilder().setNamespace(temporalNamespace).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initWorker() {
        WorkerFactory factory = applicationContext.getBean(WorkerFactory.class);
        factory.start();
    }

}
