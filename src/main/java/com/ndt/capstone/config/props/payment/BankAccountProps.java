package com.ndt.capstone.config.props.payment;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "payment.bank-transfer")
public record BankAccountProps(
    String bankId,

    String accountNo,

    String accountName
) {

}
