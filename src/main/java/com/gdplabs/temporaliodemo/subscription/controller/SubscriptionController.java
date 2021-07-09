package com.gdplabs.temporaliodemo.subscription.controller;


import com.gdplabs.temporaliodemo.subscription.model.Customer;
import com.gdplabs.temporaliodemo.subscription.service.SubscriptionService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(path = "/newSubscription", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String newSubscription(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "name") String name) {
        subscriptionService.createCustomerAndSubscribe(id, name);
        return String.format("%s started subscription", name);
    }

    @RequestMapping(path = "/cancelSubscription", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String cancelSubscription(@RequestParam(name = "id") String id) {
        subscriptionService.cancelSubscription(id);
        return "subscription cancelled";
    }

    @RequestMapping(path = "/getCustomer", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Customer> getCustomer(
            @RequestParam(name = "id") String id
    ) {
        return new ResponseEntity<>(subscriptionService.getCustomer(id), HttpStatus.OK);
    }

}


