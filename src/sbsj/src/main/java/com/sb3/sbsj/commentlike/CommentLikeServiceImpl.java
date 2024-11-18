package com.sb3.sbsj.commentlike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements ICommentLikeService {
    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    public Integer countByCommentTableUserBoard(ICommentLike searchDto) {
        if ( searchDto == null || searchDto.getCommentId() == null
                || searchDto.getCreateId() == null
                || searchDto.getCommentId() == null || searchDto.getCommentId() <= 0 ) {
            return 0;
        }
        CommentLikeDto search = CommentLikeDto.builder().build();
        search.copyFields(searchDto);
        Integer count = this.commentLikeMybatisMapper.countByCommentTableUserBoard(search);
        return count;
    }
}
