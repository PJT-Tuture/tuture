package com.tuture.service;

import com.tuture.model.dao.BoardDao;
import com.tuture.model.dto.Board;
import com.tuture.model.dto.PostBoardDto;
import com.tuture.model.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardDao boardDao;

    @Override
    public List<Board> getBoardList(){
        return boardDao.getBoardList();
    }

    @Override
    public Board findById(Long id) {
        return boardDao.getById(id);
    }

    @Override
    public int addBoard(PostBoardDto.Request request) {
        return boardDao.insertBoard(request);
    }

    @Override
    public int modifyBoard(PostBoardDto.Request request) {
        return boardDao.updateBoard(request);
    }

    @Override
    public List<Board> findBoardsByTags(Long id, Long tagId) {
        return boardDao.findBoardsByTags(id, tagId);
    }

    @Override
    public int removeBoard(Long id) {
        return boardDao.deleteBoard(id);
    }

    @Override
    public List<Board> search(SearchCondition condition) {
        return boardDao.search(condition);
    }
}
