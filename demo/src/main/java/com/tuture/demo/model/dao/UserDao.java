package com.tuture.demo.model.dao;

import com.tuture.demo.model.dto.SignUpDto;
import com.tuture.demo.model.domain.User;

public interface UserDao {
    int insertUser(SignUpDto newUser);
    User selectUserById(long id);
    User selectUserByNickname(String nickname);
    User selectUserByEmail(String email);
    int deleteUserById(long id);  // 성공 시 1, 실패시 0 반환
}

