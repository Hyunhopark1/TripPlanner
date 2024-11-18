package com.sb3.sbsj.boardfree;

import com.sb3.sbsj.boardfile.BoardFileDto;
import com.sb3.sbsj.boardfile.BoardFileListDto;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.user.IUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBoardFreeService {


    IBoardFree insert(BoardFreeDto dto, IUser user, List<MultipartFile> files);

    IBoardFree update(BoardFreeDto dto, BoardFileListDto boardFileDtoList, List<MultipartFile> files);

    IBoardFree findById(Long id);

    Boolean delete(Long id);

    List<BoardFreeDto> findAllByNameContains(SearchBoardDto dto);

    Integer countAllByNameContains(SearchBoardDto dto);

    void addViewQty(Long id,Long userId) throws Exception;

    void addLikeQty(IUser loginUser, Long id) throws Exception;

    void subLikeQty(IUser loginUser, Long id) throws Exception;

}
