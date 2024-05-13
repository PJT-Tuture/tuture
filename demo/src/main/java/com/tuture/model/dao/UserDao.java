package com.tuture.model.dao;

import com.tuture.model.domain.User;
import com.tuture.model.dto.SignUpDto;

public interface UserDao {
    int insertUser(SignUpDto newUser);
    User selectUserByNickname(String nickname);
}