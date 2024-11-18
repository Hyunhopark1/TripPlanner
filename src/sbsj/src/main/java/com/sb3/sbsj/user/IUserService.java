package com.sb3.sbsj.user;

import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import com.sb3.sbsj.commons.inif.IServiceCRUD;
import com.sb3.sbsj.security.dto.LoginRequest;

import java.util.List;

public interface IUserService extends IServiceCRUD<IUser> {
    IUser login(LoginRequest loginRequest);

    Boolean changePassword(IUser dto) throws Exception;

    IUser findByLoginId(String loginId);

    IUser findByEmail(String email);

    IUser findByName(String name);

    IUser findByNickname(String nickname);

    Integer countAllByNameContains(SearchAjaxDto dto);

    List<IUser> findAllByNameContains(SearchAjaxDto dto);

    int idCheck(String loginId);

    int emailCheck(String email);

    int nicknameCheck(String nickname);
}
