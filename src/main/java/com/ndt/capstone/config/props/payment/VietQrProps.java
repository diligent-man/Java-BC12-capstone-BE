package com.ndt.capstone.config.props.payment;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "payment.vietqr")
public record VietQrProps(
    @DefaultValue("https://img.vietqr.io/image") String url
) {
}
