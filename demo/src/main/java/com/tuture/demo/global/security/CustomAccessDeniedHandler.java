package com.tuture.demo.global.security;

import com.tuture.demo.global.exception.ErrorCode;
import com.tuture.demo.global.exception.exceptionClasses.CustomSecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        log.error("[Access is Denied] 접근 불가");
        throw new CustomSecurityException(ErrorCode.INVALID_TOKEN);
    }
}
