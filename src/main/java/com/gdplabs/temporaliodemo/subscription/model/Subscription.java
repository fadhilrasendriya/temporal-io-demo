package com.gdplabs.temporaliodemo.subscription.model;

import java.io.Serializable;
import java.time.Duration;

public class Subscription implements Serializable {

    private Duration billingPeriod;

    private int billingPeriodCharge;

    public Duration getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(Duration billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public int getBillingPeriodCharge() {
        return billingPeriodCharge;
    }

    public void setBillingPeriodCharge(int billingPeriodCharge) {
        this.billingPeriodCharge = billingPeriodCharge;
    }
}
