package com.sb3.sbsj.security.controller;

import com.sb3.sbsj.security.config.SecurityConfig;
import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private IUserService userService;

    @GetMapping("")
    private String index(Model model, @SessionAttribute(name = SecurityConfig.LOGINUSER, required = false) String nickname){
        if (nickname != null){
            IUser loginUser = this.userService.findByNickname(nickname);
            model.addAttribute(SecurityConfig.LOGINUSER, loginUser);
        }
        return "index";
    }

    @GetMapping("/signout")
    private String signout(HttpServletResponse response, HttpSession session){
        session.invalidate();

        Cookie cookie = new Cookie(SecurityConfig.LOGINUSER, null);
        cookie.setMaxAge(0); // 쿠키 저장 시간(초단위) Expires 쿠키 만료되는 날짜
        response.addCookie(cookie);
        return "login/signout";
    }
}
