package com.gdplabs.temporaliodemo.shopping.service;


import com.gdplabs.temporaliodemo.shopping.model.ShoppingOrder;
import com.gdplabs.temporaliodemo.shopping.temporal.OrderCompletionWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService {

    @Autowired
    private WorkflowClient workflowClient;

    public ShoppingOrder newOrder(String orderId) {

        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setWorkflowId("OrderCompletion_" + orderId)
                .setTaskQueue(OrderCompletionWorkflow.QUEUE_NAME)
                .build();

        OrderCompletionWorkflow workflow = workflowClient.newWorkflowStub(OrderCompletionWorkflow.class, workflowOptions);
        ShoppingOrder shoppingOrder = new ShoppingOrder(orderId, null);
        WorkflowClient.start(workflow::startWorkflow, shoppingOrder, orderId);
        return shoppingOrder;
    }

    public ShoppingOrder getOrder(String orderId) {
        OrderCompletionWorkflow workflow = workflowClient.newWorkflowStub(OrderCompletionWorkflow.class, "OrderCompletion_" + orderId);
        return workflow.getOrder();
    }

}
