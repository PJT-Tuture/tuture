package com.tuture.demo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    FAILED_REGIST_BOARD(HttpStatus.BAD_REQUEST, "게시글 저장 실패"),
    INVALID_SEARCHCONDITION(HttpStatus.BAD_REQUEST, "잘못된 검색 조건"),
    USER_NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "로그인하지 않은 유저 접근 권한 없음"),
    INCORRECT_ID(HttpStatus.FORBIDDEN, "잘못된 유저 아이디"),
    KEYWORD_IS_EMPTY(HttpStatus.BAD_REQUEST, "키워드 값 없음");

    /* 400 BAD_REQUEST : 잘못된 요청 */
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 이메일입니다"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 에러, 재 로그인 필요"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다");


    private final HttpStatus httpStatus;
    private final String description;
}
