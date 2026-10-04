package com.ndt.capstone.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import com.ndt.capstone.config.props.payment.VietQrProps;
import com.ndt.capstone.config.props.payment.BankAccountProps;
import com.ndt.capstone.payload.resp.vietqr.VietQrResponse;
import com.ndt.capstone.service.contract.external.VietQrService;


@Service
@RequiredArgsConstructor
public class VietQrServiceImpl implements VietQrService {
    private final BankAccountProps bankAccountProps;

    private final VietQrProps vietQrProps;


    public VietQrResponse generateQrCode(String amount, String description) {
        String qrCodeUrl = UriComponentsBuilder
            .fromUriString(
                String.format("%s/%s-%s-compact2.png",
                    vietQrProps.url(),
                    bankAccountProps.bankId(),
                    bankAccountProps.accountNo())
            )
            .queryParam("amount", amount)
            .queryParam("addInfo", description)
            .queryParam("accountName", URLEncoder.encode(bankAccountProps.accountName(), StandardCharsets.UTF_8))
            .build()
            .toUriString();
        // Optional: If you need raw EMVCo data text, you would invoke VietQR's official tokenized API.
        return new VietQrResponse(qrCodeUrl, null);
    }
}
