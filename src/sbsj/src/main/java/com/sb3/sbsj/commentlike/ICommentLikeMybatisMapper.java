package com.sb3.sbsj.commentlike;

import com.sb3.sbsj.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentLikeMybatisMapper {
    void insert(CommentLikeDto dto);
    void update(CommentLikeDto dto);
    void delete(CommentLikeDto dto);
    void deleteById(Long id);
    CommentLikeDto findById(Long id);

    void deleteByCommentTableUserBoard(CommentLikeDto dto);
    Integer countByCommentTableUserBoard(CommentLikeDto dto);
}
