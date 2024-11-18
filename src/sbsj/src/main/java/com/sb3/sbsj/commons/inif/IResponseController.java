package com.sb3.sbsj.commons.inif;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.IBase;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.security.config.SecurityConfig;
import com.sb3.sbsj.user.IUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface IResponseController {
    default CUDInfoDto makeResponseCheckLogin(Model model) {
        IUser loginUser = (IUser) model.getAttribute(SecurityConfig.LOGINUSER);
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        }
        return new CUDInfoDto(loginUser);
    }

    default CUDInfoDto makeResponseCheckLoginAdmin(Model model) {
        IUser loginUser = (IUser) model.getAttribute(SecurityConfig.LOGINUSER);
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        }
        return new CUDInfoDto(loginUser);
    }

    default CUDInfoDto makeResponseCheckSelfOrAdmin(Model model, IBase checkObject) {
        IUser loginUser = (IUser) model.getAttribute(SecurityConfig.LOGINUSER);
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        } else if (!loginUser.getId().equals(checkObject.getCreateId())) {
            throw new LoginAccessException("본인만 가능");
        }
        return new CUDInfoDto(loginUser);
    }

    default ResponseEntity<ResponseDto> makeResponseEntity(HttpStatus httpStatus
            , ResponseCode responseCode
            , String message
            , Object responseData) {
        ResponseDto dto = ResponseDto.builder()
                .responseCode(responseCode)
                .message(message)
                .responseData(responseData)
                .build();
        return ResponseEntity.status(httpStatus).body(dto);
    }
}
