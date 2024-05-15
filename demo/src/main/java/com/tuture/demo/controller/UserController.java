package com.tuture.demo.controller;

import com.tuture.demo.global.exception.ErrorCode;
import com.tuture.demo.global.exception.exceptionClasses.UserException;
import com.tuture.demo.model.domain.User;
import com.tuture.demo.model.dto.SignUpDto;
import com.tuture.demo.model.dto.ValidNicknameResponse;
import com.tuture.demo.service.EmailAuthService;
import com.tuture.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailAuthService emailAuthService;

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
            log.error("[generalSignUp] 회원 가입 중 오류 발생", e);
            return new ResponseEntity<>("회원 가입 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 닉네임 사용가능 여부 확인
     * @param nickname 닉네임
     * @return status, message
     */
    @GetMapping("/verify-nickname/{nickname}")
    public ResponseEntity<?> verifyNickname(@PathVariable String nickname) {
        try {
            ValidNicknameResponse validNickname = userService.isValidNickname(nickname);
            return ResponseEntity.ok(validNickname);
        } catch (Exception e) {
            log.error("[verifyNickname] 닉네임 사용가능 여부 확인 중 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 이메일로 인증 코드 보내기
     * @param email 이메일
     * @return null
     */
    @GetMapping("/send-email/{email}")
    public ResponseEntity<?> sendEmail(@PathVariable String email) {
        log.debug("[sendEmail] 이메일 인증 진행. userEmail : {} ", email);
        try {
            // 이메일 중복 체크
            if (userService.isUniqueEmail(email)) {
                // 메일 보내기
                String code = emailAuthService.sendEmailAuthMessage(email);
                // email, code 저장
                emailAuthService.saveEmailCode(email, code);
            }
            log.debug("[sendEmail] 인증코드 발송완료.");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("[sendEmail] 이메일 인증 진행 중 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 입력받은 이메일 인증 코드 확인
     */
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        log.debug("[verifyEmail] 이메일 인증 코드 검증 진행. userEmail : {} ", email);
        try {
            return ResponseEntity.ok(emailAuthService.verifyEmailCode(email, code));
        } catch (Exception e) {
            log.error("[verifyEmailCode] 이메일 인증 진행 중 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 회원 탈퇴
     * User loginUser => JWT에서 사용자 정보 가져오기
     */
    @DeleteMapping("")
    public ResponseEntity<?> removeUser(@AuthenticationPrincipal User loginUser,
                                        @RequestParam(value = "p") String password) {
        // 로그인되어있는 상태의 유저의 아이디로 가져온 유저 정보의 비밀번호와 입력받은 비밀번호가 일치하면 삭제
        User user = userService.findUserById(loginUser.getId());
        if (!user.getPassword().equals(password)) {
            throw new UserException(ErrorCode.INVALID_PASSWORD);
        }
        return new ResponseEntity<>(userService.removeUser(user) == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }





}
