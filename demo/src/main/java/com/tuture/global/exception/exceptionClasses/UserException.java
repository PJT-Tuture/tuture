package com.tuture.global.exception.exceptionClasses;

import com.tuture.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String errorMessage;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
