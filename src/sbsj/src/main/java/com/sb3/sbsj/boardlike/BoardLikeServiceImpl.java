package com.sb3.sbsj.boardlike;

import com.sb3.sbsj.commentlike.CommentLikeDto;
import com.sb3.sbsj.commentlike.ICommentLike;
import com.sb3.sbsj.commentlike.ICommentLikeMybatisMapper;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardLikeServiceImpl implements IBoardLikeService {
    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;

    @Override
    public Integer countByTableUserBoard(IBoardLike searchDto) {
        if ( searchDto == null || searchDto.getBoardId() == null
                || searchDto.getCreateId() == null
                || searchDto.getBoardId() == null || searchDto.getBoardId() <= 0 ) {
            return 0;
        }
        BoardLikeDto search = BoardLikeDto.builder().build();
        search.copyFields(searchDto);
        Integer count = this.boardLikeMybatisMapper.countByTableUserBoard(search);
        return count;
    }
}
