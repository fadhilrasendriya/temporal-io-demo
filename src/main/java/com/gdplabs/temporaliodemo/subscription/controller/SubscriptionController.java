package com.gdplabs.temporaliodemo.subscription.controller;


import com.gdplabs.temporaliodemo.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
        return "new subscription has been made";
    }

}


