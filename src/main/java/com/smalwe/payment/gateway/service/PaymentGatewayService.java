package com.smalwe.payment.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.smalwe.payment.gateway.amq.BankPaymentRequestPublisher;
import com.smalwe.payment.gateway.bean.BankPaymentRequestEvent;
import com.smalwe.payment.gateway.bean.MerchantInfo;
import com.smalwe.payment.gateway.bean.Payment;
import com.smalwe.payment.gateway.bean.PaymentPostResponse;
import com.smalwe.payment.gateway.dao.MerchantRepository;
import com.smalwe.payment.gateway.dao.PaymentRepository;
import com.smalwe.payment.gateway.enums.PaymentStatus;
import com.smalwe.payment.gateway.exception.InvalidPaymentInfoException;
import com.smalwe.payment.gateway.exception.ResourceNotFoundException;
import com.smalwe.payment.gateway.util.PaymentGatewayUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentGatewayService implements IPaymentGatewayService{

    Logger logger = LoggerFactory.getLogger(PaymentGatewayService.class);

    @Autowired
    private BankPaymentRequestPublisher paymentRequestPublisher;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    @Override
    public PaymentPostResponse createPayment(Payment payment, String apiKey) {

        logger.info("Request in PaymentService: createPayment");
        logger.info("Payment Object:" + payment);

//        logger.info("Payment Service: Card Validity : {}", PaymentGatewayUtility.isValidCreditCardNumber(payment.getCardInfo().getCardNumber()));
//        logger.info("Payment Service: CVV Validity : {}", PaymentGatewayUtility.isValidCVVNumber(payment.getCardInfo().getCvv()));

        if(PaymentGatewayUtility.isValidCreditCardNumber(payment.getCardInfo().getCardNumber()) &&
                        PaymentGatewayUtility.isValidCVVNumber(payment.getCardInfo().getCvv())) {
            logger.info("Valid card details");
        } else {
            logger.info("Invalid card details");
            throw new InvalidPaymentInfoException("Card details are incorrect");

        }

        MerchantInfo merchantInfo = merchantRepository.findByApiKey(apiKey);

        logger.info("Merchant Id:" + merchantInfo.getId());

        payment.setMerchantId(merchantInfo.getId());
        payment.setStatus(PaymentStatus.PENDING.toString());
        UUID paymentId = UUID.randomUUID();
        payment.setId(paymentId);
        payment.getCardInfo().setId(UUID.randomUUID());
        logger.info("Payment Object to be saved:" + payment);
        try {
            paymentRepository.save(payment);
        } catch (PersistenceException ex) {
            logger.info("Issue saving to DB");
            throw new PersistenceException("Issue processing the payment request");
        }

        logger.info("Payment request persisted with id:" + payment.getId());
        try {
            paymentRequestPublisher.publishMessage(createBankPaymentRequestEvent(payment));
            logger.info("Payment request published to queue with id:" + payment.getId());
        } catch(JsonProcessingException jpe) {
            logger.error("Event not published to queue: {}", jpe.getMessage());
        }

        return new PaymentPostResponse(paymentId);
    }

    @Override public Payment fetchPayment(UUID paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(payment.isPresent()) {
            payment.get().getCardInfo().setCardNumber(PaymentGatewayUtility.maskCardNumber(payment.get().getCardInfo().getCardNumber()));
            return payment.get();
        }
        else {
            throw new ResourceNotFoundException("Payment details not found");
        }
    }

    @Override public void updatePaymentStatus(UUID paymentId, String status) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(payment.isPresent()) {
            payment.get().setStatus(status);
            paymentRepository.save(payment.get());
            logger.info("Payment table updated for id: {} with status: {}", paymentId, status);
        } else {
            logger.error("Issue updating the status in payment for id: {} ", paymentId);
        }
    }

    private String createBankPaymentRequestEvent(Payment payment) throws JsonProcessingException {
        BankPaymentRequestEvent bankPaymentRequestEvent = new BankPaymentRequestEvent(
                payment.getCardInfo(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getId());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = ow.writeValueAsString(bankPaymentRequestEvent);
        return jsonString;
    }



}
