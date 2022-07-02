package com.smalwe.payment.gateway.dao;

import com.smalwe.payment.gateway.bean.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<MerchantInfo, UUID> {

    MerchantInfo findByApiKey(String apiKey);
}
