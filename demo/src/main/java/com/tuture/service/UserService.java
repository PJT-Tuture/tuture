package com.tuture.service;

import com.tuture.model.dto.SignUpDto;
import com.tuture.model.dto.ValidNicknameResponse;

public interface UserService {
    int signupUser(SignUpDto request);
    ValidNicknameResponse isValidNickname(String nickname);
    boolean isUniqueEmail(String email);
}
