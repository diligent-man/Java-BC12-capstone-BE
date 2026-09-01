package com.ndt.capstone.enums.exception;

import com.ndt.capstone.exception.BaseError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum FileError implements BaseError {
    SAVE_FILE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể lưu file"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy hoặc không thể đọc file");

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String message;
}
