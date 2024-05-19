package com.tuture.demo.model.dao;

import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.dto.SignUpDto;
import com.tuture.demo.model.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int insertUser(SignUpDto newUser);
    User selectUserById(long id);
    User selectUserByNickname(String nickname);
    User selectUserByEmail(String email);
    int deleteUserById(long id);  // 성공 시 1, 실패시 0 반환
    int updateUser(User user);
    List<Board> selectMyBoardList(@Param("id") long id, @Param("offset") int offset, @Param("limit") int limit);
    int countMyBoardList(@Param("id") long id);
}

