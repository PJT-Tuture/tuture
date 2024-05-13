package com.tuture.controller;

import com.tuture.model.dto.SignUpDto;
import com.tuture.model.dto.ValidNicknameResponse;
import com.tuture.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     * @return 1: 성공, 0: 실패
     */
    @PostMapping("/signup/general")
    public ResponseEntity<?> generalSignUp(@Valid @RequestBody SignUpDto request) {
        log.debug("[sign-up/general] 회원가입 진행. userEmail : {} ", request.getEmail());
        try {
            int result = userService.signupUser(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("[UserController] 회원 가입 중 오류 발생", e);
            return new ResponseEntity<>("회원 가입 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 닉네임 사용가능 여부 확인
     * @param nickname
     * @return status, message
     */
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> verifyNickname(@PathVariable String nickname) {
        try {
            ValidNicknameResponse validNickname = userService.isValidNickname(nickname);
            return ResponseEntity.ok(validNickname);
        } catch (Exception e) {
            log.error("[UserController] 닉네임 사용가능 여부 확인 중 오류 발생");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }





}
