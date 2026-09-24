package com.ndt.capstone.payload.request.vietqr;

import lombok.Data;


@Data
public class VietQrRequest {
    private String bankId;       // e.g., "vcb", "tcb", "mbb" or BIN number (e.g., "970436")

    private String accountNo;    // Merchant Account Number

    private Long amount;         // Order Amount

    private String description;  // Payment reference text (no Vietnamese accents recommended)

    private String accountName;  // Optional: Merchant Name
}
