package com.sb3.sbsj.user;

import com.sb3.sbsj.commons.dto.BaseDto;
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
public class UserDto extends BaseDto implements IUser {
    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private String gender;
    private String name;
    private String email;
    private String photo;
}
