package com.ndt.capstone.config.payment;

import lombok.Data;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@Configuration
@ConfigurationProperties(prefix = "payment.bank-transfer")
public class BankAccountConfig {
    private String bankId;

    private String accountNo;

    private String accountName;
}
