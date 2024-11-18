package com.sb3.sbsj.security.controller;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.security.config.SecurityConfig;
import com.sb3.sbsj.security.dto.FindIdDto;
import com.sb3.sbsj.security.dto.LoginRequest;
import com.sb3.sbsj.security.dto.SignUpRequest;
import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.IUserService;
import com.sb3.sbsj.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/selogin")
public class LoginSessionController implements IResponseController {
    @Autowired
    private IUserService userService;

    @GetMapping("/signup")
    private String viewSignUp(Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "redirect:/";
        } catch (Exception ex) {
            return "login/signup";
        }
    }

    @PostMapping("/signup")
    private String signUp(Model model, @Valid @ModelAttribute SignUpRequest dto, BindingResult bindingResult) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            CUDInfoDto checkLogin = makeResponseCheckLogin(model);
        } catch (LoginAccessException ex) {
            if (bindingResult.hasErrors()) {
                List<String> errorList = new ArrayList<>();
                for (FieldError error : bindingResult.getFieldErrors()) {
                    errorList.add(error.getField() + " : " + error.getDefaultMessage());
                    log.info(error.getDefaultMessage());
                }
                model.addAttribute("errorList", errorList);
                return "redirect:/";
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(dto);
            IUser iuser = this.userService.insert(cudInfoDto, dto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "회원가입 실패 입력정보를 다시 확인하세요");
            return "login/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    private String viewLogin(Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "redirect:/";
        } catch (Exception ex) {
            return "login/login";
        }
    }

    @PostMapping("/signin")
    private String signin(Model model, @ModelAttribute LoginRequest dto, HttpServletRequest request) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            try {
                CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
                return "redirect:/";
            } catch (Exception ex) {
            }
            IUser loginUser = this.userService.login(dto);
            if (loginUser == null) {
                model.addAttribute("message", "로그인 실패 id와 pw를 확인하세요");
                return "login/login";
            }
            HttpSession session = request.getSession();
            session.setAttribute(SecurityConfig.LOGINUSER, loginUser.getNickname());
            session.setMaxInactiveInterval(60 * 60); // 세션별 유효시간 지정
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "로그인 실패 관리자에게 문의하세요");
            return "login/login";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    private String logout(HttpServletResponse response) {
        // /logout 은 스프링 security 에서 처리하므로 이쪽 url 로 오지 않음
        return "login/signout";
    }

    @GetMapping("/findID")
    private String viewfindID(Model model){
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "redirect:/";
        } catch (Exception ex) {
            return "login/findID";
        }
    }

    @GetMapping("/findPW")
    private String viewfindPW(Model model){
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "redirect:/";
        } catch (Exception ex) {
            return "login/findPW";
        }
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam String loginId){
        return userService.idCheck(loginId);
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }

    @PostMapping("/nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestParam String nickname){
        return userService.nicknameCheck(nickname);
    }

    @GetMapping("/changePassword")
    public String viewChangePassword(Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "login/changePw";
        } catch (Exception ex) {
            return "login/login";
        }
    }
}
