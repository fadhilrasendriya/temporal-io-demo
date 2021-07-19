package com.gdplabs.temporaliodemo.shopping.config;

import com.gdplabs.temporaliodemo.shopping.service.PaymentService;
import com.gdplabs.temporaliodemo.shopping.temporal.OrderCompletionWorkflow;
import com.gdplabs.temporaliodemo.shopping.temporal.OrderCompletionWorkflowImpl;
import com.gdplabs.temporaliodemo.shopping.temporal.ShoppingActivitiesImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShoppingConfiguration {

    private final String PAYMENT_SERVICE_URL = "http://127.0.0.1:5134";

    @Bean(name = "paymentServiceClient")
    public WebClient webClient() {
        return WebClient.create(PAYMENT_SERVICE_URL);
    }

    @Bean
    @Autowired
    public ShoppingActivitiesImpl shoppingActivities(PaymentService paymentService) {
        return new ShoppingActivitiesImpl(paymentService);
    }

    @Bean(name = "shoppingWorker")
    @Autowired
    public Worker shoppingWorker(WorkerFactory workerFactory, ShoppingActivitiesImpl shoppingActivities) {
        Worker shoppingWorker = workerFactory.newWorker(OrderCompletionWorkflow.QUEUE_NAME);
        shoppingWorker.registerActivitiesImplementations(shoppingActivities);
        shoppingWorker.registerWorkflowImplementationTypes(OrderCompletionWorkflowImpl.class);
        return shoppingWorker;
    }


}
