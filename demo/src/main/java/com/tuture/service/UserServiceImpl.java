package com.tuture.service;

import com.tuture.model.dao.UserDao;
import com.tuture.model.dto.SignUpDto;
import com.tuture.model.dto.ValidNicknameResponse;
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

    @Override
    public ValidNicknameResponse isValidNickname(String nickname) {
        if (!isValidLengthNickname(nickname)) {
            return ValidNicknameResponse.builder()
                    .status(400)
                    .msg("닉네임은 2글자 이상, 10글자 이하만 가능 합니다.")
                    .build();
        }
        if (!containsSpecialCharacter(nickname)) {
            return ValidNicknameResponse.builder()
                    .status(400)
                    .msg("특수문자는 포함할 수 없습니다.")
                    .build();
        }
        if (!isUniqueNickname(nickname)){
            return ValidNicknameResponse.builder()
                    .status(400)
                    .msg("이미 존재하는 닉네임 입니다.")
                    .build();
        }
        return ValidNicknameResponse.builder().status(200).msg("사용 가능한 닉네임입니다.").build();
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return userDao.selectUserByEmail(email) == null;
    }

    private boolean isValidLengthNickname(String nickname){
        return nickname != null && nickname.length() >= 2 && nickname.length() <= 10;
    }

    private boolean containsSpecialCharacter(String nickname){
        return !nickname.matches("[a-zA-Z0-9]*");
    }

    private boolean isUniqueNickname(String nickname){
        return userDao.selectUserByNickname(nickname) == null;
    }

}
