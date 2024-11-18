package com.sb3.sbsj.boardreview;


import com.sb3.sbsj.boardfree.SearchBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardReviewMyBatisMapper {

    //게시글 등록
    void insert(BoardReviewDto dto);

    //게시글 상세정보 조회
    BoardReviewDto findById(Long id);

    //게시글 수정
    void update(BoardReviewDto dto);

    //게시글 삭제
    void delete(Long id);

    //게시글 리스트 조회
    List<BoardReviewDto> findAllByNameContains(SearchBoardDto dto);

    //게시글 수 카운트
    Integer countAllByNameContains(SearchBoardDto dto);

    //게시글 조회수 증가
    void addViewQty(Long id);

    //게시글 좋아요
    void addLikeQty(Long id);

    //게시글 좋아요 취소
    void subLikeQty(Long id);

}
