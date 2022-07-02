package com.smalwe.payment.gateway.dao;

import com.smalwe.payment.gateway.bean.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    //    @Modifying
    //    @Query("update payments set status = ?2 where u.id = ?1")
    //    void updatePaymentStatus(UUID paymentId, String status);
}
