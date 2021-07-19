package com.gdplabs.temporaliodemo.shopping.model;

import java.io.Serializable;

public class ShoppingOrder implements Serializable {

    public String id;

    public Payment payment;

    public ShoppingOrder() {

    }

    public ShoppingOrder(String id, Payment payment) {
        this.id = id;
        this.payment = payment;
    }

}
