package com.tuture.demo.service;

import com.tuture.demo.model.domain.User;
import com.tuture.demo.model.dto.SignUpDto;
import com.tuture.demo.model.dto.ValidNicknameResponse;

public interface UserService {
    int signupUser(SignUpDto request);
    ValidNicknameResponse isValidNickname(String nickname);
    boolean isUniqueEmail(String email);
    int removeUser(User user);
    User findUserById(Long id);

}
