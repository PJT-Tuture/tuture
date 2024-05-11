package com.tuture.service;

import com.tuture.model.dao.UserDao;
import com.tuture.model.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public int signupUser(SignUpDto request) {
        return userDao.insertUser(request);
    }
}
