package com.smalwe.payment.gateway.amq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class AMQConfig {

    @Bean({"bankReqQueue"})
    public Queue createBankRequestQueue() {
        return new ActiveMQQueue("bank.request.queue");
    }

    @Bean({"bankResQueue"})
    public Queue createBankResponseQueue() {
        return new ActiveMQQueue("bank.response.queue");
    }

}
