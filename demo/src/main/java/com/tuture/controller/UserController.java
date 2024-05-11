package com.tuture.controller;

import com.tuture.model.dto.SignUpDto;
import com.tuture.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    /**
     * 회원 가입
     * @return 1: 성공, 0: 실패
     */
    @PostMapping("/signup/general")
    public ResponseEntity<?> generalSignUp(@Valid @RequestBody SignUpDto request) {
        log.debug("[sign-up/general] 회원가입 진행. userEmail : {} ", request.getEmail());
        int result = userService.signupUser(request);
        return new ResponseEntity<>(result, result == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


}
