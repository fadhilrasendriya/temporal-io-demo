package com.gdplabs.temporaliodemo.shopping.temporal;

import com.gdplabs.temporaliodemo.shopping.model.Payment;
import com.gdplabs.temporaliodemo.shopping.service.PaymentService;
import io.temporal.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import io.temporal.client.ActivityCompletionFailureException;

import java.time.Duration;


public class ShoppingActivitiesImpl implements ShoppingActivities {


    private PaymentService paymentService;

    public ShoppingActivitiesImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Payment requestPayment(String orderId) throws RuntimeException {
        Mono<Payment> paymentMono = paymentService.requestPayment(orderId);
        Payment payment = paymentMono.block(Duration.ofSeconds(10));
        return payment;
    }

    @Override
    public Payment verifyPayment(String orderId) {
        Mono<Payment> paymentMono = paymentService.getPayment(orderId);
        Payment payment = paymentMono.block(Duration.ofSeconds(10));
        assert payment != null;
        if(!payment.status.equals("verified")) {
            throw new ActivityCompletionFailureException(null);
        }
        return payment;
    }

}
