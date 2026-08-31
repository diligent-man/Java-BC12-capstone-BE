package com.ndt.capstone.exception.file;


import com.ndt.capstone.enums.exception.FileError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileException extends RuntimeException {
    private final FileError error;
}
