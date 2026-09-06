package com.ndt.capstone.exception.file;


import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.FileErrMsg;


public final class FileException extends BaseException {
    public FileException(FileErrMsg errorMsg) {
        super(errorMsg, null);
    }


    public FileException(FileErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
