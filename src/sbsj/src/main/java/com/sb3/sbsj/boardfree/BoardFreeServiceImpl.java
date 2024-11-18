package com.sb3.sbsj.boardfree;


import com.sb3.sbsj.boardcomment.IBoardComment;
import com.sb3.sbsj.boardfile.BoardFileDto;
import com.sb3.sbsj.boardfile.BoardFileListDto;
import com.sb3.sbsj.boardfile.IBoardFileMybatisMapper;
import com.sb3.sbsj.boardfile.IBoardFileService;
import com.sb3.sbsj.boardlike.BoardLikeDto;
import com.sb3.sbsj.boardlike.IBoardLikeMybatisMapper;
import com.sb3.sbsj.commentlike.CommentLikeDto;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardFreeServiceImpl implements IBoardFreeService {

    private final IBoardFreeMyBatisMapper boardMyBatisMapper;

    private final IBoardLikeMybatisMapper boardLikeMybatisMapper;

    private final IBoardFileMybatisMapper boardFileMybatisMapper;

    private final IBoardFileService boardFileService;

    @Override
    @Transactional
    public IBoardFree insert(BoardFreeDto dto, IUser user, List<MultipartFile> files) {
        if (dto == null) {
            return null;
        }
        BoardFreeDto insert = BoardFreeDto.builder().build();
        insert.copyFields(dto);
        insert.setCreateId(user.getId());
        this.boardMyBatisMapper.insert(insert);
        this.boardFileService.insertFreeFiles(insert, files);
        return insert;
    }

    @Override
    @Transactional
    public IBoardFree update(BoardFreeDto dto, BoardFileListDto boardFileDtoList, List<MultipartFile> files) {
        if (dto == null || dto.getId() == null || dto.getId() <= 0)  {
            return null;
        }
        dto.setUpdateDt(dto.getSystemDt());
        BoardFreeDto update = BoardFreeDto.builder().build();
        update.copyFields(dto);
        this.boardMyBatisMapper.update(update);
        this.boardFileService.updateFiles(boardFileDtoList);
        this.boardFileService.insertFreeFiles(update, files);
        return update;
    }

    @Override
    public IBoardFree findById(Long id) {
        if (id == null || id < 0) {
            return null;
        }
        IBoardFree find = this.boardMyBatisMapper.findById(id);
        return find;
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        if (id == null || id < 0) {
            return false;
        }
        this.boardMyBatisMapper.delete(id);
        BoardFileDto search = BoardFileDto.builder().tbl(new BoardFreeDto().getTbl()).boardId(id).build();
        List<BoardFileDto> list = this.boardFileMybatisMapper.findAllByTblBoardId(search);
        for ( BoardFileDto boardFileDto : list ) {
            boardFileDto.setDeleteYn(true);
            this.boardFileMybatisMapper.delete(boardFileDto);
        }
        return true;
    }

    @Override
    public List<BoardFreeDto> findAllByNameContains(SearchBoardDto dto) {
        if (dto == null) {
            return List.of();
        }
        dto.settingValues();
        dto.setFirstIndex(dto.getFirstIndex());
        List<BoardFreeDto> list = this.boardMyBatisMapper.findAllByNameContains(dto);
        List<BoardFreeDto> result = list.stream().peek(x -> x.setCountComment( x.getCountComment().equals("0") ? "" : "[" + x.getCountComment() + "]")).toList();
        return result;
    }

    @Override
    public Integer countAllByNameContains(SearchBoardDto dto) {
        dto.settingValues();
        return this.boardMyBatisMapper.countAllByNameContains(dto);
    }

    @Override
    public void addViewQty(Long id,Long userId){
        if (id == null || id <= 0) {
           return;
        }
        // 본인 작성글일 경우 조회수 증가 x
        IBoardFree find = this.boardMyBatisMapper.findById(id);
        if(find.getCreateId().equals(userId)){
            return;
        }
        this.boardMyBatisMapper.addViewQty(id);
    }

    @Override
    @Transactional
    public void addLikeQty(IUser loginUser, Long id) {
        if (id == null || id <= 0 || loginUser == null) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(loginUser.getId())
                .boardId(id)
                .tbl(new BoardFreeDto().getTbl())
                .build();

        Integer count = this.boardLikeMybatisMapper.countByTableUserBoard(boardLikeDto);
        if (count > 0) {
            return;
        }
        //좋아요 테이블에 행 삽입/ 자유게시판 테이블에 좋아요 수 + 1
        this.boardLikeMybatisMapper.insert(boardLikeDto);
        this.boardMyBatisMapper.addLikeQty(id);
    }

    @Override
    @Transactional
    public void subLikeQty(IUser loginUser, Long id) {
        if (id == null || id <= 0 || loginUser == null) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(loginUser.getId())
                .boardId(id)
                .tbl(new BoardFreeDto().getTbl())
                .build();

        Integer count = this.boardLikeMybatisMapper.countByTableUserBoard(boardLikeDto);
        if (count < 1) {
            return;
        }
        //좋아요 테이블에 행 삭제/ 자유게시판 테이블에 좋아요 수 - 1
        this.boardLikeMybatisMapper.deleteByTableUserBoard(boardLikeDto);
        this.boardMyBatisMapper.subLikeQty(id);
    }
}
