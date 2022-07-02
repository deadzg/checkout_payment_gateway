package com.smalwe.payment.gateway.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
This contains the supported currencies by the Payment API Gateway
Follows the ISO format for currency
 */
public enum Currency {
    @JsonProperty("USD")
    USD
//    USD ("USD");
//    private String currencyCode;
//    private Currency(String currencyCode) {
//        this.currencyCode = currencyCode;
//    }
//
//    public String getCurrencyCode() {
//        return this.currencyCode;
//    }
//
//    @JsonCreator
//    public static Currency getCurrencyFromCode(String value) {
//        for (Currency currency : Currency.values()) {
//            if (currency.getCurrencyCode().equals(value)) {
//                return currency;
//            }
//        }
//        return null;
//    }

}
