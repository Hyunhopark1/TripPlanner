package com.sb3.sbsj.boardfree;

import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
public class BoardFreeServiceImplTest {

    @Autowired
    private BoardFreeServiceImpl boardFreeService;

    private UserDto user;
    private BoardFreeDto sample;

    @BeforeEach
    public void setDto() {
        this.setDto("before title", "before content", "free");
    }

    private void setDto(String title, String content, String tbl) {
        this.user = UserDto.builder()
                .id(1L)
                .build();
        this.sample = BoardFreeDto.builder()
                .id(0L)
                .title(title)
                .content(content)
                .tbl(tbl)
                .build();
    }

    @Test
    @Rollback(false) // Role 처리 안됨
    @Transactional
    public void insertTest() throws IOException {
        IBoardFree result = this.insertSample("title #1", "content #1 @2 #3", "free", this.makeSampleFiles());
        this.assertInsertCheck(user, result, sample);
    }

    private IBoardFree insertSample(String title, String content, String tbl, List<MultipartFile> files) throws IOException {
        // given
        this.setDto(title, content, tbl);

        // when
        IBoardFree result = boardFreeService.insert(this.sample, this.user, files);
        assertThat(result).isNotNull();
        IBoardFree find = this.findById(result.getId());
        return find;
    }

    private void assertInsertCheck(IUser user, IBoardFree actual, IBoardFree result) {
        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isGreaterThan(0L);
        assertThat(actual.getTitle()).isEqualTo(result.getTitle());
        assertThat(actual.getContent()).isEqualTo(result.getContent());
        assertThat(actual.getTitle()).isEqualTo(result.getTitle());
        assertThat(actual.getCreateId()).isEqualTo(user.getId());
    }

    private List<MultipartFile> makeSampleFiles() throws IOException {
        List<MultipartFile> result = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("/static/assets/img/logo.png");
        MultipartFile file1 = new MockMultipartFile("logo.png", "logo.png", "image/jpeg", resource.getInputStream());
        result.add(file1);
        return result;
    }

    @Test
    @Rollback(true) // Role 처리 됨
    @Transactional
    public void findByIdTest() throws IOException {
        // given
        IBoardFree insert = this.insertSample("findTest", "content FindTest", "free", null);
        assertThat(insert).isNotNull();

        // when
        IBoardFree find = this.findById(insert.getId());

        // given
        this.assertInsertCheck(user, find, insert);
        assertThat(find.getId()).isEqualTo(insert.getId());
        assertThat(find.getCreateDt()).isNotEmpty();
        assertThat(find.getDeleteYn()).isEqualTo(false);
        assertThat(find.getViewQty()).isEqualTo(0);
        assertThat(find.getLikeQty()).isEqualTo(0);
    }

    private IBoardFree findById(Long id) {
        return this.boardFreeService.findById(id);
    }

    @Test
    @Rollback(false) // Role 처리 안됨
    @Transactional
    public void updateTest() {
        // given
        BoardFreeDto result = (BoardFreeDto) boardFreeService.insert(this.sample, this.user, null);
        assertThat(result).isNotNull();
        result.setTitle("update title");
        result.setContent("update content @2 #3");

        // when
        result = (BoardFreeDto) boardFreeService.update(result, null, null);
        IBoardFree find = this.findById(result.getId());

        // then
        this.assertUpdateCheck(find, result);
        assertThat(find.getUpdateDt()).isNotEmpty();
    }

    private void assertUpdateCheck(IBoardFree actual, IBoardFree result) {
        // then
        assertThat(actual).isNotNull();
        assertThat(result).isNotNull();
        assertThat(actual.getId()).isEqualTo(result.getId());
        assertThat(actual.getTitle()).isEqualTo(result.getTitle());
        assertThat(actual.getContent()).isEqualTo(result.getContent());
        assertThat(actual.getCreateId()).isEqualTo(result.getCreateId());
//        assertThat(actual.getViewQty()).isEqualTo(result.getViewQty());
//        assertThat(actual.getLikeQty()).isEqualTo(result.getLikeQty());
    }

    @Test
    @Rollback(true) // Role 처리 됨
    @Transactional
    public void deleteTest() {
    }
}
