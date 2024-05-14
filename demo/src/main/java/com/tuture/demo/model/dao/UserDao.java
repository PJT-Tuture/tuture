package com.tuture.demo.model.dao;

import com.tuture.demo.model.dto.SignUpDto;
import com.tuture.demo.model.domain.User;

public interface UserDao {
    int insertUser(SignUpDto newUser);
    User selectUserByNickname(String nickname);
    User selectUserByEmail(String email);
    int deleteUser(Long id);
}