package com.smalwe.payment.gateway.amq;

import com.smalwe.payment.gateway.bean.BankPaymentRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import javax.jms.Queue;

@Component
public class BankPaymentRequestPublisher {

    Logger logger = LoggerFactory.getLogger(BankPaymentRequestPublisher.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("bankReqQueue")
    private Queue queue;

    public String publishMessage (String event) {

        jmsTemplate.convertAndSend(queue, event);
        logger.info("Message published:" + event);
        return "Success";
    }

}
