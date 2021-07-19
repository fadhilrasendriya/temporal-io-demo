package com.gdplabs.temporaliodemo.shopping.temporal;

import com.gdplabs.temporaliodemo.shopping.model.ShoppingOrder;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderCompletionWorkflow {

    String QUEUE_NAME = "OrderCompletion";

    @WorkflowMethod
    void startWorkflow(ShoppingOrder shoppingOrder, String orderId);

    @QueryMethod
    ShoppingOrder getOrder();

}
