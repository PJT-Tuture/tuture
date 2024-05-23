package com.tuture.demo.global.exception;


import com.tuture.demo.global.exception.exceptionClasses.BoardException;
import com.tuture.demo.global.exception.exceptionClasses.SigninException;
import com.tuture.demo.global.exception.exceptionClasses.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BoardException이 발생했을 때 처리 방법
     */
    @ExceptionHandler(BoardException.class)
    public ResponseEntity<ErrorResponse> handleBoardException(BoardException e) {
        log.debug("[BoardException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        log.debug("[UserException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(SigninException.class)
    public ResponseEntity<ErrorResponse> handleSignInException(SigninException e) {
        log.error("[SigninException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
