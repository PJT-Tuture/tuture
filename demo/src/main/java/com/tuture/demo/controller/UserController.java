package com.tuture.demo.controller;

import com.tuture.demo.global.exception.ErrorCode;
import com.tuture.demo.global.exception.exceptionClasses.SigninException;
import com.tuture.demo.global.exception.exceptionClasses.UserException;
import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.domain.User;
import com.tuture.demo.model.dto.*;
import com.tuture.demo.service.EmailAuthService;
import com.tuture.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailAuthService emailAuthService;

    /**
     * 회원 가입
     *
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
     *
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
     *
     * @param email 이메일
     * @return null
     */
    @GetMapping("/send-email/{email}")
    public ResponseEntity<?> sendEmail(@PathVariable String email) throws Exception {
        log.debug("[sendEmail] 이메일 인증 진행. userEmail : {} ", email);
        // 이메일 중복 체크
        String code = null;
        if (userService.isUniqueEmail(email)) {
            // 메일 보내기
//            String code = emailAuthService.sendEmailAuthMessage(email);
            code = emailAuthService.sendEmailAuth(email);
            // email, code 저장
            emailAuthService.saveEmailCode(email, code);
        } else {
            return new ResponseEntity<>("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST);
        }
        log.info(code);
        return ResponseEntity.ok(code);
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
        System.out.println(loginUser.getId());
        User user = userService.findUserById(loginUser.getId());
        if (!user.getPassword().equals(password)) {
            throw new UserException(ErrorCode.INVALID_PASSWORD);
        }
        return new ResponseEntity<>(userService.removeUser(user) == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    /**
     * 일반 로그인
     */
    @PostMapping("/signin/general")
    public ResponseEntity<?> generalSignIn(@RequestBody SignInDto.Request request) {
        // 1. 해당 이메일로 가져온 회원.
        User user = userService.findUserByEmail(request.getEmail());

        // password가 입력받은 password와 일치하지 않으면 예외
        if (!user.getPassword().equals(request.getPassword())) {
            throw new SigninException(ErrorCode.USER_NOT_FOUND);
        }

        return ResponseEntity.ok(userService.signIn(user));
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("")
    public ResponseEntity<User> getUserDetail(@AuthenticationPrincipal User loginUser) {
        System.out.println(loginUser.getId());
        return ResponseEntity.ok(loginUser);
    }

    @PutMapping("/basic")
    public ResponseEntity<?> modifyUserBasic(@AuthenticationPrincipal User loginUser,
                                             @RequestPart(value = "json") UpdateUserBasicDTO.Request request,
                                             @RequestPart(required = false) MultipartFile profileImg) {
        try {
            File imageFolder = new File("profileImg/");
            if (!imageFolder.exists()) {
                imageFolder.mkdir();
            }
            if (!profileImg.isEmpty() && profileImg.getSize() != 0) {
                String today = Long.toString(System.currentTimeMillis());
                String newImageName = today + "_" + profileImg.getOriginalFilename();

                File saveFile = new File(imageFolder.getAbsolutePath(), newImageName);

                profileImg.transferTo(saveFile);
                loginUser.setProfileImage(newImageName);
            }

            if (!request.getNickname().equals("")) {
                loginUser.setNickname(request.getNickname());
            }

            // 사용자 정보 업데이트 호출
            User updatedUser = userService.modifyUser(loginUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("유저 정보 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/{imgFileName}")
    public ResponseEntity<?> getImage(@PathVariable String imgFileName) {
        File file = new File("image", imgFileName);
        Resource imgResource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imgFileName + "\"")
                .body(imgResource);
    }

    @PutMapping("/password")
    public ResponseEntity<?> modifyUserPassword(@AuthenticationPrincipal User loginUser,
                                                @RequestBody UpdatePasswordDto request) {
        if (!loginUser.getPassword().equals(request.getCurPassword())) {
            throw new UserException(ErrorCode.INCORRECT_PASSWORD);
        }
        loginUser.setPassword(request.getNewPassword());
        userService.modifyUser(loginUser);
        return ResponseEntity.ok().build();
    }

    /**
     * 내가 작성한 글 불러오기
     *
     * @param loginUser 현재 로그인한 유저
     * @param page      페이지네이션을 위한 현재 페이지 값. default: 0
     * @return 10개의 게시글
     */
    @GetMapping("/myboard")
    public ResponseEntity<?> getMyBoardList(@AuthenticationPrincipal User loginUser,
                                            @RequestParam(value = "page", defaultValue = "1") int page) {
        BoardListResponse myBoardList = userService.getMyBoardList(loginUser.getId(), page);
        return ResponseEntity.ok(myBoardList);
    }
}

