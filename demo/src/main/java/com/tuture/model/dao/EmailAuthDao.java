package com.tuture.model.dao;

import com.tuture.model.dto.EmailCodeDto;

public interface EmailAuthDao {
    void insertCode(EmailCodeDto newUser);
    EmailCodeDto selectByEmail(String email);
}
