package com.smalwe.payment.gateway.bean;

import java.util.UUID;

public class BankPaymentRequestEvent {
    private CardInfo cardInfo;
    private Double amount;
    private String currency;
    private UUID paymentId;

    public BankPaymentRequestEvent() {
    }

    public BankPaymentRequestEvent(CardInfo cardInfo, Double amount, String currency, UUID paymentId) {
        super();
        this.cardInfo = cardInfo;
        this.amount = amount;
        this.currency = currency;
        this.paymentId = paymentId;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public String toString() {
        return "PaymentId:" + this.getPaymentId() +
                " CardNumber:" + this.getCardInfo().getCardNumber();
    }
}
