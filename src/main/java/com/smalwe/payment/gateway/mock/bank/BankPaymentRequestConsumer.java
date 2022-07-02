package com.smalwe.payment.gateway.mock.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalwe.payment.gateway.amq.BankPaymentResponseConsumer;
import com.smalwe.payment.gateway.bean.BankPaymentRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BankPaymentRequestConsumer {

    Logger logger = LoggerFactory.getLogger(BankPaymentRequestConsumer.class);

    @Autowired
    private IBankService mockBankService;

    @JmsListener(destination = "bank.request.queue")
    public void onMessage(String event) throws JsonProcessingException {

        logger.info("Message received from Bank Request Queue:" + event);
        ObjectMapper mapper = new ObjectMapper();
        BankPaymentRequestEvent bankPaymentRequestEvent = mapper.readValue(event, BankPaymentRequestEvent.class);
        logger.info("Message Coverted from JSON String to Object: {}", bankPaymentRequestEvent.toString());

        mockBankService.processPayment(bankPaymentRequestEvent);
    }
}
