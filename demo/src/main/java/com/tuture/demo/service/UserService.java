package com.tuture.demo.service;

import com.tuture.demo.model.domain.User;
import com.tuture.demo.model.dto.BoardListResponse;
import com.tuture.demo.model.dto.SignInDto;
import com.tuture.demo.model.dto.SignUpDto;
import com.tuture.demo.model.dto.ValidNicknameResponse;

public interface UserService {
    int signupUser(SignUpDto request);
    ValidNicknameResponse isValidNickname(String nickname);
    boolean isUniqueEmail(String email);
    int removeUser(User user);
    User findUserById(long id);
    User findUserByEmail(String email);
    SignInDto.Response signIn(User user);
    User modifyUser(User user);
    BoardListResponse getMyBoardList(long id, int page);
}
