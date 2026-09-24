package com.ndt.capstone.config.payment;

import lombok.*;


import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@Configuration
@ConfigurationProperties(prefix = "payment.vietqr")
public class VietQrConfig {
    private String url;
}
