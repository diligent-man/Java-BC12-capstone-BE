package com.ndt.capstone.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import com.ndt.capstone.config.payment.VietQrConfig;
import com.ndt.capstone.config.payment.BankAccountConfig;
import com.ndt.capstone.payload.response.vietqr.VietQrResponse;
import com.ndt.capstone.service.contract.external.VietQrService;


@Service
@RequiredArgsConstructor
public class VietQrServiceImpl implements VietQrService {
    private final BankAccountConfig bankAccountConfig;

    private final VietQrConfig vietQrConfig;


    public VietQrResponse generateQrCode(String amount, String description) {
        String qrCodeUrl = UriComponentsBuilder
            .fromUriString(
                String.format("%s/%s-%s-compact2.png",
                    vietQrConfig.getUrl(),
                    bankAccountConfig.getBankId(),
                    bankAccountConfig.getAccountNo())
            )
            .queryParam("amount", amount)
            .queryParam("addInfo", description)
            .queryParam("accountName", URLEncoder.encode(bankAccountConfig.getAccountName(), StandardCharsets.UTF_8))
            .build()
            .toUriString();
        // Optional: If you need raw EMVCo data text, you would invoke VietQR's official tokenized API.
        return new VietQrResponse(qrCodeUrl, null);
    }
}
