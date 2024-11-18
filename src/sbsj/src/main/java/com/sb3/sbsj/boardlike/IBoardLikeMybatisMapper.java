package com.sb3.sbsj.boardlike;

import com.sb3.sbsj.commentlike.CommentLikeDto;
import com.sb3.sbsj.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardLikeMybatisMapper {
    void insert(BoardLikeDto dto);
    void update(BoardLikeDto dto);
    void delete(BoardLikeDto dto);
    void deleteById(Long id);
    BoardLikeDto findById(Long id);

    void deleteByTableUserBoard(BoardLikeDto dto);
    Integer countByTableUserBoard(BoardLikeDto searchDto);
}
