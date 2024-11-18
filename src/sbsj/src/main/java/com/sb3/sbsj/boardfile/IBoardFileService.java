package com.sb3.sbsj.boardfile;

import com.sb3.sbsj.boardfree.IBoardFree;
import com.sb3.sbsj.boardreview.IBoardReview;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.inif.IServiceCRUD;
import com.sb3.sbsj.user.IUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBoardFileService {
    IBoardFile insert(CUDInfoDto cudInfoDto, IBoardFile dto);
    IBoardFile update(CUDInfoDto cudInfoDto, IBoardFile dto);
    Boolean delete(IBoardFile dto);

    Boolean deleteById(Long id);
    IBoardFile findById(Long id);
    List<IBoardFile> findAllByTblBoardId(IBoardFile search);

    Boolean insertFreeFiles(IBoardFree boardFreeDto, List<MultipartFile> files) throws RuntimeException;
    Boolean insertReviewFiles(IBoardReview boardReview, List<MultipartFile> files) throws RuntimeException;
    Boolean updateFiles(BoardFileListDto boardFileDtoList);
    byte[] getBytesFromFile(IBoardFile down);
}
