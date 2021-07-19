package com.gdplabs.temporaliodemo.shopping.controller;

import com.gdplabs.temporaliodemo.shopping.model.Payment;
import com.gdplabs.temporaliodemo.shopping.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class TestPaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(path = "/testapi/getPayment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> getPayment(@RequestParam(name = "orderid") String orderId) {
        Mono<Payment> response = paymentService.getPayment(orderId);
        Payment payment = response.block();
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @RequestMapping(path = "/testapi/newPayment",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Payment> newPayment(@RequestBody Map<String, Object> body) {
        String orderId = body.get("orderid").toString();
        System.out.println("Order is: " + orderId);
        Mono<Payment> response = paymentService.requestPayment(orderId);
        Payment payment = response.block();
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

}
