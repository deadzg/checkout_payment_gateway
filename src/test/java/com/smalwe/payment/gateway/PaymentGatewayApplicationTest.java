package com.smalwe.payment.gateway;

import com.smalwe.payment.gateway.controller.PaymentGatewayController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentGatewayApplicationTest {

    @Autowired
    PaymentGatewayController paymentGatewayController;

    @Test
    public void contextLoads() {

    }
}
