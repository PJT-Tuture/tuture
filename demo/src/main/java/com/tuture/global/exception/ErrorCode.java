package com.tuture.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 이메일입니다");

    private final HttpStatus httpStatus;
    private final String description;
}
