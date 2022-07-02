package com.smalwe.payment.gateway.controller;

import com.smalwe.payment.gateway.bean.CardInfo;
import com.smalwe.payment.gateway.bean.Payment;
import com.smalwe.payment.gateway.bean.PaymentPostResponse;
import com.smalwe.payment.gateway.exception.ResourceNotFoundException;
import com.smalwe.payment.gateway.security.ApiKeyAuthManager;
import com.smalwe.payment.gateway.security.RestAuthenticationEntryPoint;
import com.smalwe.payment.gateway.service.PaymentGatewayService;
import com.smalwe.payment.gateway.service.PaymentGatewayServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentGatewayController.class)
public class PaymentGatewayControllerTest {

    private static final String VALID_API_KEY = "apiKey1";
    private static final String INVALID_API_KEY = "invalidapiKey1";

    @MockBean PaymentGatewayService paymentGatewayService;


    @Autowired MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testCreatePayment_ValidRequest() throws Exception {
        Payment payment = new Payment();
        PaymentPostResponse paymentPostResponse = new PaymentPostResponse(UUID.randomUUID());

        Mockito.when(paymentGatewayService.createPayment(payment,VALID_API_KEY)).thenReturn(paymentPostResponse);

        mockMvc.perform(post("/v1/payments").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON).
                        content("{\"cardInfo\":{\"cardNumber\":\"5999999999999109\",\"expiryMonth\":\"1\",\"expiryYear\":2022,\"cvv\":\"234\",\"nameOnCard\":\"smalwe\"},\"amount\":15.20,\"currency\":\"USD\",\"description\":\"Purchased Shoes\"}").
                        header("API_KEY", VALID_API_KEY)).
                        andExpect(status().isCreated());
    }

    @Test
    public void testCreatePayment_InvalidAPIKey() throws Exception {
        Payment payment = new Payment();
        PaymentPostResponse paymentPostResponse = new PaymentPostResponse(UUID.randomUUID());

        Mockito.when(paymentGatewayService.createPayment(payment,INVALID_API_KEY)).thenReturn(paymentPostResponse);

        mockMvc.perform(post("/v1/payments").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                content("{\"cardInfo\":{\"cardNumber\":\"5999999999999109\",\"expiryMonth\":\"1\",\"expiryYear\":2022,\"cvv\":\"234\",\"nameOnCard\":\"smalwe\"},\"amount\":15.20,\"currency\":\"USD\",\"description\":\"Purchased Shoes\"}").
                header("API_KEY", INVALID_API_KEY)).
                andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreatePayment_InvalidJson() throws Exception {
        Payment payment = new Payment();
        PaymentPostResponse paymentPostResponse = new PaymentPostResponse(UUID.randomUUID());

        Mockito.when(paymentGatewayService.createPayment(payment,VALID_API_KEY)).thenReturn(paymentPostResponse);

        mockMvc.perform(post("/v1/payments").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                content("{\"cardInfo\"{\"cardNumber\":\"5999999999999109\",\"expiryMonth\":\"1\",\"expiryYear\":2022,\"cvv\":\"234\",\"nameOnCard\":\"smalwe\"},\"amount\":15.20,\"currency\":\"USD\",\"description\":\"Purchased Shoes\"}").
                header("API_KEY", VALID_API_KEY)).
                andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPayment_ValidRequest() throws Exception {

        UUID paymentId = UUID.randomUUID();

        Payment payment = new Payment();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setId(UUID.randomUUID());
        cardInfo.setCardNumber("5999999999999109");
        cardInfo.setCvv("089");
        cardInfo.setExpiryMonth(12);
        cardInfo.setExpiryYear(2023);
        cardInfo.setNameOnCard("Test User");
        payment.setCardInfo(cardInfo);
        payment.setAmount(123.45);
        payment.setCurrency("USD");
        payment.setId(paymentId);

        Mockito.when(paymentGatewayService.fetchPayment(paymentId)).thenReturn(payment);

        mockMvc.perform(get("/v1/payments/"+ paymentId.toString()).
                accept(MediaType.APPLICATION_JSON).
                header("API_KEY", VALID_API_KEY)).
                andExpect(status().isOk());
    }

    @Test
    public void testGetPayment_ResourceNotFound() throws Exception {

        UUID paymentId = UUID.randomUUID();

        Payment payment = new Payment();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setId(UUID.randomUUID());
        cardInfo.setCardNumber("5999999999999109");
        cardInfo.setCvv("089");
        cardInfo.setExpiryMonth(12);
        cardInfo.setExpiryYear(2023);
        cardInfo.setNameOnCard("Test User");
        payment.setCardInfo(cardInfo);
        payment.setAmount(123.45);
        payment.setCurrency("USD");
        payment.setId(paymentId);

        Mockito.when(paymentGatewayService.fetchPayment(paymentId)).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/v1/payments"+ UUID.randomUUID().toString()).
                accept(MediaType.APPLICATION_JSON).
                header("API_KEY", VALID_API_KEY)).
                andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
            return new RestAuthenticationEntryPoint();
        }

        @Bean
        public ApiKeyAuthManager apiKeyAuthManager() {
            return new ApiKeyAuthManager(Arrays.asList(VALID_API_KEY));
        }

    }
}
