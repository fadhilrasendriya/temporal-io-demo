package com.gdplabs.temporaliodemo.subscription.model;

import java.io.Serializable;

public class Customer implements Serializable {

    private String id;
    private String name;
    private Subscription subscription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
