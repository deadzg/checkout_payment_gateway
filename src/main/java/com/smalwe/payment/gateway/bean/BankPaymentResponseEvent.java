package com.smalwe.payment.gateway.bean;

import java.util.UUID;

public class BankPaymentResponseEvent {

    private String status;
    private UUID paymentId;

    public BankPaymentResponseEvent() {
    }

    public BankPaymentResponseEvent(String status, UUID paymentId) {
        this.status = status;
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}
