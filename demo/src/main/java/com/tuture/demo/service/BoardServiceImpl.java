package com.tuture.demo.service;

import com.tuture.demo.model.dao.BoardDao;
import com.tuture.demo.model.dto.Board;
import com.tuture.demo.model.dto.AddBoardDto;
import com.tuture.demo.model.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDao boardDao;

    @Override
    public List<Board> getBoardList(){
        return boardDao.getBoardList();
    }

    @Override
    public Board findBoardById(Long id) {
        return boardDao.getBoardById(id);
    }

    @Override
    @Transactional
    public int addBoard(AddBoardDto.Request request) {
        Board board = AddBoardDto.Request.addBoardDtoToBoard(request);
        return boardDao.insertBoard(board);
    }

    @Override
    @Transactional
    public int modifyBoard(Board board) {
        return boardDao.updateBoard(board);
    }

    @Override
    @Transactional
    public int removeBoard(Long id) {
        return boardDao.deleteBoard(id);
    }

    @Override
    public List<Board> search(int page, SearchCondition condition) {
        return boardDao.search(page, condition);
    }
}
