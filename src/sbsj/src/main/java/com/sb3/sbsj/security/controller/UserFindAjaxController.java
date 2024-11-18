package com.sb3.sbsj.security.controller;

import com.sb3.sbsj.security.dto.FindIdDto;
import com.sb3.sbsj.security.dto.FindIdResponseDto;
import com.sb3.sbsj.security.dto.FindPwDto;
import com.sb3.sbsj.security.dto.FindPwResponseDto;
import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequestMapping("/api/find")
@RestController
public class UserFindAjaxController {
    @Autowired
    private IUserService userService;

    @PostMapping("/findid")
    private ResponseEntity<FindIdResponseDto> findID(@Validated @RequestBody FindIdDto dto) {
        FindIdResponseDto responseDto = new FindIdResponseDto();
        try {
            if (dto.getEmail().isEmpty() || dto.getName().isEmpty()) {
                responseDto.setMessage("email과 name을 입력하세요");
                return ResponseEntity.badRequest().body(responseDto);
            }
            IUser user = userService.findByEmail(dto.getEmail());
            if (user != null && user.getName().equals(dto.getName())) {
                responseDto.setLoginId(user.getLoginId());
                responseDto.setMessage("ID찾기 성공");
                return ResponseEntity.ok().body(responseDto);
            } else {
                responseDto.setMessage("일치하는 ID가 없습니다");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
            responseDto.setMessage("찾기 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }
    @PostMapping("/findpw")
    private ResponseEntity<FindPwResponseDto> findPW(@Validated @RequestBody FindPwDto dto) {
        FindPwResponseDto responseDto = new FindPwResponseDto();
        try {
            if (dto.getEmail().isEmpty() || dto.getName().isEmpty() || dto.getLoginId().isEmpty()) {
                responseDto.setMessage("loginId와 email, name을 입력하세요");
                return ResponseEntity.badRequest().body(responseDto);
            }
            IUser user = userService.findByLoginId(dto.getLoginId());
            if (user != null && user.getName().equals(dto.getName()) && user.getEmail().equals(dto.getEmail())) {
                String randomValue = getRandomString(8);    //  숫자 + 문자 + 특수문자 조합 문자열 생성
                user.setPassword(randomValue);
                userService.changePassword(user);
                responseDto.setPassword(user.getPassword());
                responseDto.setMessage("비밀번호가 초기화 성공");
                return ResponseEntity.ok().body(responseDto);
            } else {
                responseDto.setMessage("일치하는 ID가 없습니다");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
            responseDto.setMessage("pw 초기화 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    private final char[] rndCharSet = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '!', '@', '#', '^', '?', '*', '+'
            // 원하는 특수문자 추가해서 사용
    };
    public String getRandomString(int size) {
        StringBuilder sb = new StringBuilder();

        Random rnd = new Random(new Date().getTime());

        int len = rndCharSet.length;

        for (int i = 0; i < size; i++) {
            sb.append(rndCharSet[rnd.nextInt(len)]);
        }

        return sb.toString();
    }
}
