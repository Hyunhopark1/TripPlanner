package com.sb3.sbsj.security.dto;

import com.sb3.sbsj.user.IUser;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest extends LoginRequest implements IUser {
    private Long id;
    @Size(min = 6, max = 20, message = "아이디는 6~20 글자 사이입니다")
    private String loginId;
    @Size(min = 8, max = 20, message = "비밀번호는 8~20 글자 사이입니다")
    private String password;
    @Size(min = 4, max = 20, message = "닉네임은 4~20 글자 사이입니다")
    private String nickname;
    private String gender;
    @Size(min = 2, max = 20, message = "이름은 2~20 글자 사이입니다")
    private String name;
    @Size(min = 1, max = 150, message = "이메일은 1~150자 이어야 합니다.")
    private String email;
    private String photo;
}