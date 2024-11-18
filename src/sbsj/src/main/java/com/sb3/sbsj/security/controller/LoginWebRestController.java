package com.sb3.sbsj.security.controller;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.inif.ICommonRestController;
import com.sb3.sbsj.security.config.SecurityConfig;
import com.sb3.sbsj.security.dto.LoginRequest;
import com.sb3.sbsj.security.dto.SignUpRequest;
import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/login")
public class LoginWebRestController implements ICommonRestController {
    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    private ResponseEntity<ResponseDto> signup(Model model, @Valid @RequestBody SignUpRequest dto, BindingResult bindingResult) {
        try {
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(dto);
            IUser result = this.userService.insert(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/signout")
    private ResponseEntity<ResponseDto> signout(HttpSession session){
        session.invalidate();
        return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", true);
    }

    @Override
    public ResponseEntity<ResponseDto> insert(Model model, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> deleteById(Model model, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> findById(Model model, Long id) {
        return null;
    }
}
