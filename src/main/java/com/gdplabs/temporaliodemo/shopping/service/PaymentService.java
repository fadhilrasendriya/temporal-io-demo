package com.gdplabs.temporaliodemo.shopping.service;

import com.gdplabs.temporaliodemo.shopping.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class PaymentService {

    private final WebClient paymentServiceClient;

    @Autowired
    public PaymentService(WebClient paymentServiceClient) {
        this.paymentServiceClient = paymentServiceClient;
    }

    public Mono<Payment> requestPayment(String orderId) {

        Map<String, Object> map = new HashMap<>();
        map.put("orderid", orderId);

        return paymentServiceClient.post()
                .uri("/payment/newPayment")
                .body(Mono.just(map), Map.class)
                .retrieve()
                .bodyToMono(Payment.class);

    }

    public Mono<Payment> getPayment(String orderId) {
        return paymentServiceClient.get()
                .uri("/payment/getPaymentStatus?orderid=" + orderId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Payment.class);

    }


}
