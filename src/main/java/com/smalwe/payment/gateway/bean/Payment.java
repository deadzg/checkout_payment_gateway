package com.smalwe.payment.gateway.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private UUID id;

    @Valid
    @NotNull(message = "CardInfo is mandatory")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="card_id", referencedColumnName = "id")
    private CardInfo cardInfo;


    @NotNull(message = "Amount is mandatory")
    private Double amount; //Cannot be negative

    @NotNull (message = "Currency is mandatory")
    @Size(min = 3, max= 3, message = "Invalid Currency")
    private String currency;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="merchant_id", referencedColumnName = "id")
    private Integer merchantId;

    private String status; // Make it Enum

    private String description;

    public Payment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return  "Payment Id:" + this.getId() +
                " Card Details:" + this.getCardInfo() +
                " Amount:" + this.getAmount() +
                " Currency:" + this.getCurrency() +
                " Description:" + this.getDescription() +
                " Merchant:" + this.getMerchantId();

    }
}