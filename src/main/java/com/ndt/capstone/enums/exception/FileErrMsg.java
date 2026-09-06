package com.ndt.capstone.enums.exception;


import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum FileErrMsg implements ErrorMsg {
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "File not found"),
    FILE_EXISTED(HttpStatus.CONFLICT, "A file of that name already exists."),

    FILE_UPLOADED(HttpStatus.OK, "Upload file successfully."),
    FILE_UPLOAD_FAILED(HttpStatus.EXPECTATION_FAILED, "Upload file failed."),

    FILE_READ(HttpStatus.OK, "Read file successfully."),
    FILE_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Read file successfully."),

    FILE_DELETED(HttpStatus.OK, "Delete file successfully."),
    FILE_DELETED_ALL(HttpStatus.OK, "Delete files successfully."),
    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String errorMsg;
}
