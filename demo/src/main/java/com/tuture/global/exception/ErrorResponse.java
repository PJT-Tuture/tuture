package com.tuture.global.exception;

import com.tuture.global.exception.errorCode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int status;
    private ErrorCode errorCode;
    private String errorMessage;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(e.getHttpStatus().value())
                        .errorCode(e)
                        .errorMessage(e.getDescription()).build());
    }
}
