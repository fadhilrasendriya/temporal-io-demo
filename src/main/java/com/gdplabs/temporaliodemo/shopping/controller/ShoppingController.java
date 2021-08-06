package com.gdplabs.temporaliodemo.shopping.controller;

import com.gdplabs.temporaliodemo.shopping.model.ShoppingOrder;
import com.gdplabs.temporaliodemo.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @RequestMapping(path = "/completeOrder",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    public ResponseEntity<ShoppingOrder> startOrderCompletion(@RequestBody Map<String, Object> body) {
        String orderId = body.get("orderid").toString();
        ShoppingOrder shoppingOrder = shoppingService.newOrder(orderId);
        return new ResponseEntity<>(shoppingOrder, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/getOrder",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET
    )
    public ResponseEntity<ShoppingOrder> getOrder(@RequestParam(name = "orderid") String orderId) {
        ShoppingOrder shoppingOrder = shoppingService.getOrder(orderId);
        return new ResponseEntity<>(shoppingOrder, HttpStatus.OK);
    }



}
