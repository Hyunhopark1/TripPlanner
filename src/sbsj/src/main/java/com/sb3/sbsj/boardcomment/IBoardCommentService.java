package com.sb3.sbsj.boardcomment;

import com.sb3.sbsj.boardfree.SearchBoardDto;
import com.sb3.sbsj.user.IUser;

import java.util.List;

public interface IBoardCommentService {
    IBoardComment addLikeQty(IUser loginUser, Long id);
    IBoardComment subLikeQty(IUser loginUser, Long id);

    IBoardComment insert(IUser user, BoardCommentDto dto);

    IBoardComment update(IUser user, BoardCommentDto dto);

    IBoardComment findById(Long id);

    Boolean delete(Long id);

    Integer countAllByBoardId(SearchBoardDto dto);
    List<BoardCommentDto> findAllByBoardIdTbl(SearchBoardCommentDto dto, IUser loginUser);
}