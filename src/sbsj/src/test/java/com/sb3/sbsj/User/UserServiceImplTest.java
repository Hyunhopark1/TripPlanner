package com.sb3.sbsj.User;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.IUserService;
import com.sb3.sbsj.user.UserDto;
import com.sb3.sbsj.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplTest {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    private UserDto user;

    @BeforeEach
    public void setDto() {
        this.setDto("before LoginId", "before NickName", "before Email");
    }

    private void setDto(String loginId, String nickname, String email) {
        this.user = UserDto.builder()
                .id(1L)
                .loginId(loginId)
                .password("asdasd")
                .nickname(nickname)
                .gender("man")
                .name("Name")
                .email(email)
                .photo("M")
                .build();
    }

    @Test
    @Order(1)
    @Transactional
    @Rollback(true)
    public void UserInsertTest() throws Exception {
        CUDInfoDto cudInfoDto = new CUDInfoDto(UserDto.builder().nickname("junitest").build());
        UserDto insert = UserDto.builder().build();
        IUser resultInsert2 = userService.insert(cudInfoDto, insert);
        assertThat(resultInsert2).isNull();

        UserDto insert2 = UserDto.builder()
                .loginId("MyLoginId12311123123123we3qrq3q31rfq1w3rftq1w3tfq13tfsqw3tfqw31tfq1 rfqw33frwae3fgAWe3tgw2t")
                .password("MyPassword123")
                .nickname("MyNickname123")
                .gender("man")
                .name("MyName123")
                .email("MyEmail123@asd")
                .photo("MyEmail123@asd")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> {
            userService.insert(cudInfoDto, insert2);
        });
        log.error("Exception : {}", exception.toString());
        insert2.setLoginId("MynameS");
        IUser resultInsert = userService.insert(cudInfoDto, insert2);
        assertThat(resultInsert).isNotNull();
        assertThat(resultInsert.getId()).isGreaterThan(0L);
//        assertThat(resultInsert.getPassword()).isEqualTo(insert2.getPassword());
        assertThat(resultInsert.getLoginId()).isEqualTo(insert2.getLoginId());
        assertThat(resultInsert.getNickname()).isEqualTo(insert2.getNickname());
        assertThat(resultInsert.getGender()).isEqualTo(insert2.getGender());
        assertThat(resultInsert.getName()).isEqualTo(insert2.getName());
        assertThat(resultInsert.getEmail()).isEqualTo(insert2.getEmail());
        assertThat(resultInsert.getPhoto()).isEqualTo(insert2.getPhoto());
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback(true)
    public void UserUpdateTest() throws Exception {
        CUDInfoDto cudInfoDto = new CUDInfoDto(this.user);
        UserDto result = (UserDto) userService.insert(cudInfoDto, this.user);
        assertThat(result).isNotNull();
        result.setLoginId("update LoginId");
        result.setNickname("update Nickname");
        result.setEmail("update Email");

        userService.update(cudInfoDto, result);
        IUser find = this.userService.findById(result.getId());

        this.assertUpdateCheck(find, result);
    }

    private void assertUpdateCheck(IUser actual, IUser result) {
        // then
        assertThat(actual).isNotNull();
        assertThat(result).isNotNull();
        assertThat(actual.getId()).isEqualTo(result.getId());
        assertThat(actual.getLoginId()).isEqualTo(result.getLoginId());
        assertThat(actual.getNickname()).isEqualTo(actual.getNickname());
        assertThat(actual.getGender()).isEqualTo(actual.getGender());
        assertThat(actual.getName()).isEqualTo(actual.getName());
        assertThat(actual.getEmail()).isEqualTo(actual.getEmail());
        assertThat(actual.getPhoto()).isEqualTo(actual.getPhoto());
    }

    private void assertDeleteCheck(IUser actual, IUser result) {
        assertThat(actual).isNotNull();
        assertThat(result).isNotNull();
        assertThat(actual.getId()).isEqualTo(result.getId());
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(true)
    public void UserDeleteTest() throws Exception {
        CUDInfoDto cudInfoDto = new CUDInfoDto(this.user);
        UserDto result = (UserDto) userService.insert(cudInfoDto, this.user);
        assertThat(result).isNotNull();

        IUser findIUser = this.userService.findById(1L);
        userService.deleteById(this.user.getId());
        this.assertDeleteCheck(findIUser, result);
    }
}
