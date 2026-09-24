package com.ndt.capstone.service.contract.external;

import com.ndt.capstone.payload.response.vietqr.VietQrResponse;


public interface VietQrService {
    /**
     Construct standard VietQR Template URL
     @return baseUrl/{bankId}-{accountNo}-compact.png?amount={amount}&addInfo={description}&accountName={accountName}
    */
    VietQrResponse generateQrCode(String amount, String description);
}
