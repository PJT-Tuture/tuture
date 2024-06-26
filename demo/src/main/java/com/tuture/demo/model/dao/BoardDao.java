package com.tuture.demo.model.dao;

import com.tuture.demo.model.domain.Board;

import java.util.List;

public interface BoardDao {

    List<Board> getBoardList();

    Board getBoardById(Long id);
    int insertBoard(Board board);

    int updateBoard(Board board);
    int deleteBoard(Long id);

    List<Board> getBoardsByTagIds(List<Long> tagIds);
}
