package com.sb3.sbsj.user;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.ICommonRestController;
import com.sb3.sbsj.security.config.SecurityConfig;
import com.sb3.sbsj.security.dto.ChangePasswordDto;
import com.sb3.sbsj.security.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/vi/user")
public class UserWebRestController implements ICommonRestController<UserDto> {
    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto> insert(Model model, @Validated @RequestBody UserDto dto) {
        try {
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLoginAdmin(model);
            IUser result = this.userService.insert(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> update(Model model, @Validated @PathVariable Long id, @Validated @RequestBody UserDto dto) {
        try {
            if (id == null || dto == null || !id.equals(dto.getId())) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IUser find = this.userService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckSelfOrAdmin(model, find);
            IUser result = this.userService.update(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            this.userService.findById(id);
            makeResponseCheckLoginAdmin(model);
            Boolean result = this.userService.deleteById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            makeResponseCheckLogin(model);
            IUser find = this.userService.findById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", find);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDto> changePassword(Model model, @Validated @RequestBody ChangePasswordDto dto) throws Exception {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            IUser loginUser = cudInfoDto.getLoginUser();
            if(dto.getNewPassword().length() <= 7 ){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R888881, "비밀번호는 8글자 이상입니다.", null);
            }
            if(!dto.getNewPassword().equals(dto.getCheckNewPassword())){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R888881, "비밀번호가 일치하지 않습니다.", null);
            }

            loginUser.setPassword(dto.getNewPassword());
            userService.changePassword(loginUser);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "비밀번호 변경 성공", null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, "비밀번호 변경 실패", null);
        }
    }

    @PostMapping("/signin")
    private ResponseEntity<ResponseDto> login(Model model, @RequestBody LoginRequest dto, HttpServletRequest request){
        try {
            if(dto == null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051,"입력 매개변수 에러", null);
            }
            IUser loginUser = this.userService.login(dto);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R000074, "로그인 실패 했습니다. ID와 암호를 확인하세요", null);
            }
            HttpSession session = request.getSession();
            session.setAttribute(SecurityConfig.LOGINUSER, loginUser.getNickname());
            session.setMaxInactiveInterval(60 * 60);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", true);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }
}