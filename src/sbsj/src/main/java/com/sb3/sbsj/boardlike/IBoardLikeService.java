package com.sb3.sbsj.boardlike;

import com.sb3.sbsj.commons.inif.IServiceCRUD;

public interface IBoardLikeService {
    Integer countByTableUserBoard(IBoardLike searchDto);
}
