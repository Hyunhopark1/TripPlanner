package com.sb3.sbsj.boardfile;

import com.sb3.sbsj.boardfree.BoardFreeDto;
import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import com.sb3.sbsj.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardFileMybatisMapper extends IMybatisCRUD<BoardFileDto> {

    void insert(BoardFreeDto dto);
    void update(BoardFreeDto dto);
    void delete(BoardFileDto dto);

    BoardFileDto findById(Long id);

    Integer countAllByTblBoardId(BoardFileDto search);
    List<BoardFileDto> findAllByTblBoardId(BoardFileDto search);

    int countAllByNameContains(SearchAjaxDto dto);
    List<BoardFileDto> findAllByNameContains(SearchAjaxDto dto);
}
