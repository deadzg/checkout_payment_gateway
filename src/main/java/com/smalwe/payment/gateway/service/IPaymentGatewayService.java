package com.smalwe.payment.gateway.service;

import com.smalwe.payment.gateway.bean.Payment;
import com.smalwe.payment.gateway.bean.PaymentPostResponse;

import java.util.UUID;

public interface IPaymentGatewayService {

    PaymentPostResponse createPayment(Payment payment, String apiKey);

    Payment fetchPayment(UUID paymentId);

    void updatePaymentStatus (UUID paymentId, String status);
}
