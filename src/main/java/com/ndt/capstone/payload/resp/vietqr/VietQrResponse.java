package com.ndt.capstone.payload.resp.vietqr;

import lombok.Data;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
public class VietQrResponse {
    private String qrUrl;  // Direct link to the QR image

    private String qrData; // The underlying EMVCo text payload
}
