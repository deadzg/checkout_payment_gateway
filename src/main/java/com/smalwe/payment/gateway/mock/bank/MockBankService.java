package com.smalwe.payment.gateway.mock.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.smalwe.payment.gateway.bean.BankPaymentRequestEvent;
import com.smalwe.payment.gateway.bean.BankPaymentResponseEvent;
import com.smalwe.payment.gateway.bean.Payment;
import com.smalwe.payment.gateway.service.PaymentGatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MockBankService implements IBankService{

    Logger logger = LoggerFactory.getLogger(MockBankService.class);

    @Autowired
    private BankPaymentResponsePublisher bankPaymentResponsePublisher;

    @Override public void processPayment(BankPaymentRequestEvent bankPaymentRequestEvent) {
        /* Any business logic can go here.
         For the sake of simplicity, since it's a mock bank it will send SUCCESS for below cardNumber
         == Valid Card NUmbers ==
        4999999999999109
        5431111111111111
        5123455806308521
        ======================
        Anything apart from this will be returned as FAILED
         */

        logger.info("Bank Payment Request getting processed with id: {}", bankPaymentRequestEvent.getPaymentId());
        List<String> validCardNumbers = Arrays.asList(new String []{"4999999999999109", "5431111111111111", "5123455806308521"});

        BankPaymentResponseEvent bankPaymentResponseEvent;
        if(validCardNumbers.contains(bankPaymentRequestEvent.getCardInfo().getCardNumber())) {

            logger.info("Bank Payment Request Service sent request to publisher with status SUCCESS with id: {}", bankPaymentRequestEvent.getPaymentId());
            bankPaymentResponseEvent = new BankPaymentResponseEvent("SUCCESS", bankPaymentRequestEvent.getPaymentId());


        } else {
            logger.info("Bank Payment Request Service sent request to publisher with status FAILED with id: {}", bankPaymentRequestEvent.getPaymentId());
            bankPaymentResponseEvent = new BankPaymentResponseEvent("FAILED", bankPaymentRequestEvent.getPaymentId());
        }

        try {
            bankPaymentResponsePublisher.publishMessage(createBankPaymentResponseEvent(bankPaymentResponseEvent.getPaymentId(),bankPaymentResponseEvent.getStatus()));
        }
        catch(JsonProcessingException jpe) {
            logger.error("Event not published to bank response queue: {}", jpe.getMessage());
        }
    }

    private String createBankPaymentResponseEvent(UUID paymentId, String status) throws JsonProcessingException {
        BankPaymentResponseEvent bankPaymentResponseEvent = new BankPaymentResponseEvent(status, paymentId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = ow.writeValueAsString(bankPaymentResponseEvent);
        return jsonString;
    }

}
