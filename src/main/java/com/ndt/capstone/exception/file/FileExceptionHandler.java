package com.ndt.capstone.exception.file;

import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.exception.auth.AuthException;
import com.ndt.capstone.payload.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class FileExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
            FileException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAuthException(FileException ex) {
        return buildApiErrorResponse(ex.getError());
    }
}
