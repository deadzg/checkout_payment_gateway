package com.smalwe.payment.gateway.bean;

import java.util.UUID;

public class PaymentPostResponse {

    private UUID id;

    public PaymentPostResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
