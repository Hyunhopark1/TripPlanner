package com.sb3.sbsj.user;

import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import com.sb3.sbsj.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMybatisMapper extends IMybatisCRUD<UserDto> {
    UserDto findByLoginId(String loginId);

    UserDto findByNickname(String nickname);

    void changePassword(UserDto dto);

    UserDto findByEmail(String email);

    UserDto findByName(String name);

    Integer countAllByNameContains(SearchAjaxDto search);

    List<UserDto> findAllByNameContains(SearchAjaxDto search);

    int idCheck(String loginId);

    int emailCheck(String email);

    int nicknameCheck(String nickname);
}
