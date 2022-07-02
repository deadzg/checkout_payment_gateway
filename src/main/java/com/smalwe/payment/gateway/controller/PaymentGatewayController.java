package com.smalwe.payment.gateway.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.smalwe.payment.gateway.bean.ApiError;
import com.smalwe.payment.gateway.bean.Payment;
import com.smalwe.payment.gateway.bean.PaymentPostResponse;
import com.smalwe.payment.gateway.dao.MerchantRepository;
import com.smalwe.payment.gateway.enums.Currency;
import com.smalwe.payment.gateway.enums.PaymentMethod;
import com.smalwe.payment.gateway.enums.PaymentStatus;
import com.smalwe.payment.gateway.service.PaymentGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import org.h2.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
public class PaymentGatewayController {

    Logger logger = LoggerFactory.getLogger(PaymentGatewayController.class);

    @Autowired
    private PaymentGatewayService paymentGatewayService;


    @Operation(summary = "Submit a payment request")
    @RequestMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentPostResponse createPayment(@Valid @RequestBody Payment payment, @RequestHeader("API_KEY") String apiKey) {
        logger.info("Request in Payment Gateway Controller");
        return paymentGatewayService.createPayment(payment, apiKey);

    }

    @Operation(summary = "Get an already submitted payment request information")
    @RequestMapping(
            value = "/{paymentId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Payment getPayment(@PathVariable UUID paymentId) {
        logger.info("Request GET Payment");
        return paymentGatewayService.fetchPayment(paymentId);

    }
}
