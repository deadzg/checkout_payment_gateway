package com.smalwe.payment.gateway.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


/*
4999999999999109
5431111111111111
5123455806308521
 */
@Entity
public class CardInfo {

    @Id
    @Column(name="id")
    private UUID id;

    @Column(name="card_number")
    @NotNull (message = "cardNumber is mandatory")
    private String cardNumber;

    @NotNull (message = "expiryMonth is mandatory")
    @Min(value = 1, message = "Month cannot be less than 1")
    @Max(value = 12, message = "Month cannot be greater than 12")
    private Integer expiryMonth; // Validation 1 - 12

    @NotNull (message = "expiryYear is mandatory")
    @Min(value = 2022, message = "Year cannot be less than the current year")
    private Integer expiryYear; // Year in future

    @NotNull (message = "cvv is mandatory")
    @Size(min = 3, max= 4, message = "Invalid CVV Code")
    private String cvv; // 3 or 4 digit

    @NotNull (message = "nameOnCard is mandatory")
    private String nameOnCard;

    public CardInfo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String toString() {
        return  "Card Id:" + this.getId() +
                " Card Number:" + this.getCardNumber() +
                " CVV:" + this.getCvv() +
                " Expiry Month:" + this.getExpiryMonth() +
                " Expiry Year:" + this.getExpiryYear() +
                " Name:" + this.getNameOnCard();

    }
}
