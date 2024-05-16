package com.tuture.demo.service;


import com.tuture.demo.model.dto.SignUpDto;
import jakarta.validation.Valid;

public interface UserService {
    int signupUser(@Valid SignUpDto request);

}
