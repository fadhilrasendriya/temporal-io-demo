package com.gdplabs.temporaliodemo.shopping.model;

import java.io.Serializable;

public class Payment implements Serializable {
    public String id;

    public String orderId;

    public String status;

    public Payment() {

    }

    public Payment(String id, String orderId, String status) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
    }

}
