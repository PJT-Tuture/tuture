package com.tuture.demo.model.dao;

import com.tuture.demo.model.dto.EmailCodeDto;

public interface EmailAuthDao {
    void insertCode(EmailCodeDto newUser);
    EmailCodeDto selectByEmail(String email);
}
