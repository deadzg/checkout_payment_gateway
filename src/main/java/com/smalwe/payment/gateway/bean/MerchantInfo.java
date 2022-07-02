package com.smalwe.payment.gateway.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="merchants")
public class MerchantInfo {

    @Id
    private Integer id;

    @Column(name="api_key")
    private String apiKey;

    public MerchantInfo() {
    }

    public MerchantInfo(Integer id, String apiKey) {
        super();
        this.id = id;
        this.apiKey = apiKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
