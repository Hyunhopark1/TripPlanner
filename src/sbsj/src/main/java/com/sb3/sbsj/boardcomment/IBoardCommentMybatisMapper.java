package com.sb3.sbsj.boardcomment;

import com.sb3.sbsj.boardcomment.BoardCommentDto;
import com.sb3.sbsj.boardfree.SearchBoardDto;
import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import com.sb3.sbsj.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardCommentMybatisMapper {
    void addLikeQty(Long id);
    void subLikeQty(Long id);
    void insert(BoardCommentDto dto);
    void update(BoardCommentDto dto);
    void delete(Long id);
    BoardCommentDto findById(Long id);

    Integer countAllByBoardId(SearchBoardDto Dto);
    List<BoardCommentDto> findAllByBoardIdTbl(SearchBoardCommentDto dto);
}