package com.smalwe.payment.gateway.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.smalwe.payment.gateway.amq.BankPaymentRequestPublisher;
import com.smalwe.payment.gateway.bean.CardInfo;
import com.smalwe.payment.gateway.bean.MerchantInfo;
import com.smalwe.payment.gateway.bean.Payment;
import com.smalwe.payment.gateway.bean.PaymentPostResponse;
import com.smalwe.payment.gateway.dao.MerchantRepository;
import com.smalwe.payment.gateway.dao.PaymentRepository;
import com.smalwe.payment.gateway.exception.InvalidPaymentInfoException;
import com.smalwe.payment.gateway.exception.ResourceNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class PaymentGatewayServiceTest {

    @InjectMocks
    private PaymentGatewayService paymentGatewayService;


    @Mock
    private BankPaymentRequestPublisher paymentRequestPublisher;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    public PaymentRepository paymentRepository;

    private Payment payment;

//    @Before
//    public void setupGeneric() throws Exception {
//        MockitoAnnotations.openMocks(this);
//    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID paymentId = UUID.randomUUID();

        payment = new Payment();
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
    }

    @Test
    public void testCreatePayment_Valid() {
        String API_KEY = "apiKey";
        MerchantInfo merchantInfo = new MerchantInfo(123, API_KEY);
        Mockito.when(merchantRepository.findByApiKey(API_KEY)).thenReturn(merchantInfo);
        Mockito.when(paymentRequestPublisher.publishMessage("testString")).thenReturn("testString");

        PaymentPostResponse paymentPostResponse = paymentGatewayService.createPayment(payment, API_KEY);

        assertEquals(paymentPostResponse.getId(), payment.getId());

    }

    @Test
    public void testCreatePayment_InValid_CardNumber() {
        String API_KEY = "apiKey";
        CardInfo cardInfo = payment.getCardInfo();
        cardInfo.setCardNumber("1233333");
        MerchantInfo merchantInfo = new MerchantInfo(123, API_KEY);
        Mockito.when(merchantRepository.findByApiKey(API_KEY)).thenReturn(merchantInfo);
        Mockito.when(paymentRequestPublisher.publishMessage("testString")).thenReturn("testString");

        InvalidPaymentInfoException thrown = Assertions.assertThrows(InvalidPaymentInfoException.class, () -> paymentGatewayService.createPayment(payment, API_KEY));

        Assertions.assertTrue(thrown.getMessage().contains("incorrect"));

    }

    @Test
    public void testCreatePayment_PersistenceException() {
        String API_KEY = "apiKey";
        MerchantInfo merchantInfo = new MerchantInfo(123, API_KEY);
        Mockito.when(merchantRepository.findByApiKey(API_KEY)).thenReturn(merchantInfo);
        Mockito.when(paymentRepository.save(payment)).thenThrow(new PersistenceException());
        Mockito.when(paymentRequestPublisher.publishMessage("testString")).thenReturn("testString");

        PersistenceException thrown = Assertions.assertThrows(PersistenceException.class, () -> paymentGatewayService.createPayment(payment, API_KEY));

        Assertions.assertTrue(thrown.getMessage().contains("Issue processing "));

    }
}
