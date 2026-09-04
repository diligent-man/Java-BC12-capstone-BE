package com.ndt.capstone.exception.auth;

import lombok.Getter;
import com.ndt.capstone.enums.exception.AuthError;

@Getter
public class AuthException extends RuntimeException {
    private final AuthError error;
    private final String customMessage;

    // Constructor cũ - giữ nguyên để không phải sửa các chỗ khác
    public AuthException(AuthError error) {
        super(error.getMessage());
        this.error = error;
        this.customMessage = error.getMessage();
    }

    // Constructor mới - dùng khi cần gắn số lần còn lại vào message
    public AuthException(AuthError error, String customMessage) {
        super(customMessage);
        this.error = error;
        this.customMessage = customMessage;
    }
}