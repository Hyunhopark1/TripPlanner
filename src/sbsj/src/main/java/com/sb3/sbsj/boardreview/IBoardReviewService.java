package com.sb3.sbsj.boardreview;

import com.sb3.sbsj.boardfile.BoardFileDto;
import com.sb3.sbsj.boardfile.BoardFileListDto;
import com.sb3.sbsj.boardfree.SearchBoardDto;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.user.IUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBoardReviewService {


    IBoardReview insert(BoardReviewDto dto, IUser user, List<MultipartFile> files);

    IBoardReview update(BoardReviewDto dto, BoardFileListDto boardFileDtoList, List<MultipartFile> files);

    IBoardReview findById(Long id);

    Boolean delete(Long id);

    List<BoardReviewDto> findAllByNameContains(SearchBoardDto dto);

    Integer countAllByNameContains(SearchBoardDto dto);

    void addViewQty(Long id) throws Exception;

    void addLikeQty(IUser loginUser, Long id) throws Exception;

    void subLikeQty(IUser loginUser, Long id) throws Exception;
}
