package com.smalwe.payment.gateway.mock.bank;

import com.smalwe.payment.gateway.amq.BankPaymentRequestPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class BankPaymentResponsePublisher {

    Logger logger = LoggerFactory.getLogger(BankPaymentRequestPublisher.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("bankResQueue")
    private Queue queue;

    public String publishMessage (String event) {

        jmsTemplate.convertAndSend(queue, event);
        logger.info("Message published:" + event);
        return "Success";
    }
}
