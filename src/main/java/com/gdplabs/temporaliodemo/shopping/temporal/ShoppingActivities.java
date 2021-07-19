package com.gdplabs.temporaliodemo.shopping.temporal;

import com.gdplabs.temporaliodemo.shopping.model.Payment;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ShoppingActivities {

    @ActivityMethod
    Payment requestPayment(String orderId);

    @ActivityMethod
    Payment verifyPayment(String orderId);
}
