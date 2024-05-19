package com.tuture.demo.model.dao;

import com.tuture.demo.model.dto.BoardLanguageDto;

public interface BoardLanguageDao {
    int insertBoardLanguage(Long boardId, Long tagId);

}
