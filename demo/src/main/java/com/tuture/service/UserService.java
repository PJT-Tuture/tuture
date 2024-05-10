package com.tuture.service;

import com.tuture.model.dto.SignUpDto;
import jakarta.validation.Valid;

public interface UserService {
    int signupUser(@Valid SignUpDto request);

}
