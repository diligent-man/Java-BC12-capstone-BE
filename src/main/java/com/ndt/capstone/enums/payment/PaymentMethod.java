package com.ndt.capstone.enums.payment;

import lombok.Getter;
import lombok.AllArgsConstructor;


@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH_BASED("cash-based", "cash"),
    BANK_TRANSFER("bank transfer", "bank transfer"),
    DIGITAL_WALLET("digital wallet", "digital wallet"),
    CARD("card", "card"),
    CASH_BASED_VOUCHER("cash-based voucher", "voucher"),
    ;

    private final String name;

    private final String alterName;


    public static PaymentMethod fromName(String name) {
        for (PaymentMethod pm : values()) {
            if (pm.name.equals(name)) {
                return pm;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + name);
    }


    public static PaymentMethod fromAlterName(String alterName) {
        for (PaymentMethod pm : values()) {
            if (pm.alterName.equals(alterName)) {
                return pm;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + alterName);
    }
}
