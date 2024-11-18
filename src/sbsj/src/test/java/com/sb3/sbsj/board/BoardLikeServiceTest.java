//package com.sb3.sbsj.board;
//
//import com.sb3.sbsj.boardcomment.BoardCommentDto;
//import com.sb3.sbsj.boardcomment.IBoardComment;
//import com.sb3.sbsj.boardcomment.IBoardCommentService;
//import com.sb3.sbsj.boardlike.BoardLikeDto;
//import com.sb3.sbsj.boardlike.IBoardLikeMybatisMapper;
//import com.sb3.sbsj.user.IUser;
//import com.sb3.sbsj.user.UserDto;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@Slf4j
//@SpringBootTest
//@Transactional
//public class BoardLikeServiceTest {
//    @Autowired
//    private IBoardCommentService boardCommentService;
//
//    private UserDto user;
//    private BoardCommentDto sample;
//
//    @BeforeEach
//    public void setDto() {
//        this.setDto(1L, "before comment", "free");
//    }
//
//    private void setDto(Long boardId, String comment, String tbl) {
//        this.user = UserDto.builder()
//                .id(1L)
//                .build();
//        this.sample = BoardCommentDto.builder()
//                .id(0L)
//                .boardId(boardId)
//                .comment(comment)
//                .tbl(tbl)
//                .build();
//    }
//
//    @Test
//    public void insertTest() throws IOException {
//        IBoardComment result = this.insertSample(1L, "comment #1 @2 #3", "free");
//        this.assertInsertCheck(user, result, sample);
//
//        Throwable exception = assertThrows(Exception.class, () -> {
//            IBoardComment result1 = this.insertSample(1L, "", "free");
//            this.assertInsertCheck(user, result1, sample);
//        });
//        System.out.println(exception.toString());
//        exception = assertThrows(Exception.class, () -> {
//            IBoardComment result1 = this.insertSample(null, "comment", "free");
//            this.assertInsertCheck(user, result1, sample);
//        });
//        System.out.println(exception.toString());
//    }
//
//    private IBoardComment insertSample(Long boardId, String comment, String tbl) throws IOException {
//        this.setDto(boardId, comment, tbl);
//
//        IBoardComment result = boardCommentService.insert(this.user, this.sample);
//        assertThat(result).isNotNull();
//        IBoardComment find = this.findById(result.getId());
//        return find;
//    }
//
//    private void assertInsertCheck(IUser user, IBoardComment actual, IBoardComment result) {
//        assertThat(actual).isNotNull();
//        assertThat(actual.getId()).isGreaterThan(0L);
//        assertThat(actual.getBoardId()).isEqualTo(result.getBoardId());
//        assertThat(actual.getComment()).isEqualTo(result.getComment());
//        assertThat(actual.getTbl()).isEqualTo(result.getTbl());
//        assertThat(actual.getCreateId()).isEqualTo(user.getId());
//    }
//
//    @Test
//    public void findByIdTest() throws IOException {
//        IBoardComment insert = this.insertSample(1L, "conmment FindTest", "free");
//        assertThat(insert).isNotNull();
//
//        IBoardComment find = this.findById(insert.getId());
//
//        this.assertInsertCheck(user, find, insert);
//        assertThat(find.getId()).isEqualTo(insert.getId());
//        assertThat(find.getCreateDt()).isNotEmpty();
//        assertThat(find.getDeleteYn()).isEqualTo(false);
//    }
//
//    private IBoardComment findById(Long id) {
//        return this.boardCommentService.findById(id);
//    }
//
//    @Test
//    public void updateTest() {
//        BoardCommentDto result = (BoardCommentDto) boardCommentService.insert(this.user, this.sample);
//        assertThat(result).isNotNull();
//        result.setComment("update comment");
//
//        result = (BoardCommentDto) boardCommentService.update(user, result);
//        IBoardComment find = this.boardCommentService.findById(result.getId());
//
//        this.assertUpdateCheck(find, result);
//        assertThat(find.getUpdateDt()).isNotEmpty();
//    }
//
//    private void assertUpdateCheck(IBoardComment actual, IBoardComment result) {
//        assertThat(actual).isNotNull();
//        assertThat(result).isNotNull();
//        assertThat(actual.getId()).isEqualTo(result.getId());
//        assertThat(actual.getComment()).isEqualTo(result.getComment());
//        assertThat(actual.getTbl()).isEqualTo(result.getTbl());
//        assertThat(actual.getCreateId()).isEqualTo(result.getCreateId());
//    }
//
//    @Test
//    public void deleteTest() {
//        BoardCommentDto insert = (BoardCommentDto) boardCommentService.insert(this.user, this.sample);
//        assertThat(insert).isNotNull();
//
//        Boolean result = boardCommentService.delete(insert.getId());
//
//        assertThat(result).isEqualTo(true);
//    }
//}
//
