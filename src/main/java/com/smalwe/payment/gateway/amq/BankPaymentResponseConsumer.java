package com.smalwe.payment.gateway.amq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalwe.payment.gateway.bean.BankPaymentRequestEvent;
import com.smalwe.payment.gateway.bean.BankPaymentResponseEvent;
import com.smalwe.payment.gateway.controller.PaymentGatewayController;
import com.smalwe.payment.gateway.service.IPaymentGatewayService;
import org.apache.tomcat.jni.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BankPaymentResponseConsumer {

    Logger logger = LoggerFactory.getLogger(BankPaymentResponseConsumer.class);

    @Autowired
    private IPaymentGatewayService paymentGatewayService;

    ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "bank.response.queue")
    public void onMessage(String event) throws JsonProcessingException {

        logger.info("Message received:" + event);

        BankPaymentResponseEvent bankPaymentResponseEvent = mapper.readValue(event, BankPaymentResponseEvent.class);
        logger.info("Message Coverted from JSON String to Object: {}", bankPaymentResponseEvent.toString());

        paymentGatewayService.updatePaymentStatus(bankPaymentResponseEvent.getPaymentId(), bankPaymentResponseEvent.getStatus());
    }

}
