package com.sb3.sbsj.security.dto;

import com.sb3.sbsj.commons.dto.BaseNullRequest;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends BaseNullRequest {
    @Size(min = 5, max = 20, message = "로그인ID는 5~20 사이여야 합니다.")
    private String loginId;
    @Size(min = 6, max = 20, message = "비밀번호는 6~20 사이여야 합니다.")
    private String password;
}
