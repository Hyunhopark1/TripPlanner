package com.sb3.sbsj.boardcomment;

import com.sb3.sbsj.boardfree.SearchBoardDto;
import com.sb3.sbsj.commentlike.CommentLikeDto;
import com.sb3.sbsj.commentlike.ICommentLikeMybatisMapper;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardCommentServiceImpl implements IBoardCommentService {
    @Autowired
    private IBoardCommentMybatisMapper boardCommentMybatisMapper;

    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    @Transactional
    public IBoardComment addLikeQty(IUser loginUser, Long id) {
        if (id == null || id <= 0 || loginUser == null) {
            return null;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .createId(loginUser.getId())
                .commentId(id)
                .build();

        Integer count = this.commentLikeMybatisMapper.countByCommentTableUserBoard(commentLikeDto);
        if (count > 0) {
            return null;
        }
        this.commentLikeMybatisMapper.insert(commentLikeDto);
        this.boardCommentMybatisMapper.addLikeQty(id);

        IBoardComment find = this.boardCommentMybatisMapper.findById(id);
        return find;
    }

    @Override
    @Transactional
    public IBoardComment subLikeQty(IUser loginUser, Long id) {
        if (id == null || id <= 0 || loginUser == null) {
            return null;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .createId(loginUser.getId())
                .commentId(id)
                .build();

        Integer count = this.commentLikeMybatisMapper.countByCommentTableUserBoard(commentLikeDto);
        if (count < 1) {
            return null;
        }
        this.commentLikeMybatisMapper.deleteByCommentTableUserBoard(commentLikeDto);
        this.boardCommentMybatisMapper.subLikeQty(id);

        IBoardComment find = this.boardCommentMybatisMapper.findById(id);
        return find;
    }

    @Override
    public Integer countAllByBoardId(SearchBoardDto dto) {
        if ( dto == null ) {
            return 0;
        }
        Integer result = this.boardCommentMybatisMapper.countAllByBoardId(dto);
        return result;
    }

    @Override
    public List<BoardCommentDto> findAllByBoardIdTbl(SearchBoardCommentDto dto, IUser loginUser) {
        if (dto.getBoardId() == null || loginUser == null) {
            return List.of();
        }
        dto.setCreateId(loginUser.getId());
        dto.settingCommentValues();
        dto.setFirstIndex(dto.getFirstIndex());
        return this.boardCommentMybatisMapper.findAllByBoardIdTbl(dto);
    }

    @Override
    public IBoardComment insert(IUser user, BoardCommentDto dto) {
        if (dto == null) {
            return null;
        }
        BoardCommentDto insert = BoardCommentDto.builder().build();
        insert.copyFields(dto);
        insert.setCreateId(user.getId());
        this.boardCommentMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public IBoardComment update(IUser user, BoardCommentDto dto) {
        if (dto == null || dto.getId() == null || dto.getId() <= 0)  {
            return null;
        }
        dto.setUpdateDt(dto.getSystemDt());
        BoardCommentDto update = BoardCommentDto.builder()
                .id(dto.getId())
                .comment(dto.getComment())
                .boardId(dto.getBoardId())
                .updateDt(dto.getUpdateDt())
                .build();
        update.copyFields(dto);
        update.setCreateId(user.getId());
        this.boardCommentMybatisMapper.update(update);
        return update;
    }

    @Override
    public Boolean delete(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        this.boardCommentMybatisMapper.delete(id);
        return true;
    }

    @Override
    public BoardCommentDto findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        BoardCommentDto find = this.boardCommentMybatisMapper.findById(id);
        if (find == null) {
            throw new IdNotFoundException(String.format("Error: not found id = %d!", id));
        }
        return find;
    }
}