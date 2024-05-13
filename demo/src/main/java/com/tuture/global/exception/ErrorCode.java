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
    INVALID_LENGTH_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임은 2글자 이상, 10글자 이하만 가능 합니다."),
    INCLUDE_SPECIAL_CHARACTER_NICKNAME(HttpStatus.BAD_REQUEST, "특수문자는 포함할 수 없습니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 이메일입니다");

    private final HttpStatus httpStatus;
    private final String description;
}
