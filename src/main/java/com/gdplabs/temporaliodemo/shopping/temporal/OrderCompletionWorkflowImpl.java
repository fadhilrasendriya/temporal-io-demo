package com.gdplabs.temporaliodemo.shopping.temporal;

import com.gdplabs.temporaliodemo.shopping.model.Payment;
import com.gdplabs.temporaliodemo.shopping.model.ShoppingOrder;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class OrderCompletionWorkflowImpl implements OrderCompletionWorkflow{

    private ShoppingOrder shoppingOrder;

    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(10))
            .setBackoffCoefficient(1)
            .build();

    private final ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setRetryOptions(retryOptions)
            .setStartToCloseTimeout(Duration.ofSeconds(20))
            .build();

    private final ShoppingActivities shoppingActivities = Workflow.newActivityStub(ShoppingActivities.class, activityOptions);

    @Override
    public void startWorkflow(ShoppingOrder shoppingOrder, String orderId) {
        this.shoppingOrder = shoppingOrder;

        this.shoppingOrder.payment = shoppingActivities.requestPayment(orderId);

        this.shoppingOrder.payment = shoppingActivities.verifyPayment(orderId);



    }

    @Override
    public ShoppingOrder getOrder() {
        return shoppingOrder;
    }
}
