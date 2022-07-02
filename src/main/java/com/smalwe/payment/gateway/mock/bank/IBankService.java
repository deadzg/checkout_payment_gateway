package com.smalwe.payment.gateway.mock.bank;

import com.smalwe.payment.gateway.bean.BankPaymentRequestEvent;
import com.smalwe.payment.gateway.bean.BankPaymentResponseEvent;

public interface IBankService {

    void processPayment(BankPaymentRequestEvent bankPaymentRequestEvent);
}
