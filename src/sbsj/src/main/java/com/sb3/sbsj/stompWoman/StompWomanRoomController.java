package com.sb3.sbsj.stompWoman;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.user.IUser;
import com.sb3.sbsj.user.IUserService;
import com.sb3.sbsj.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/stompWoman")
public class StompWomanRoomController implements IResponseController {
    @Autowired
    private StompWomanRoomService stompWomanRoomService;

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public String stompWomanRoomList(Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            IUser user = cudInfoDto.getLoginUser();
            if (user.getGender() == null || user.getGender().isEmpty()) {
                return "redirect:/";
            }
            if (user.getGender().equals("man")) {
                return "redirect:/";
            } else {
                List<StompWomanRoomDto> list = this.stompWomanRoomService.findAll();
                model.addAttribute("stompWomanRoomList", list);
                return "stompWoman/stompWomanproomlist";
            }
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/enter")   // GET ? 와 &
//    @GetMapping("/enter/{roomId}/{writer}") // GET 주소로 데이터 전달
    public String enterStompWomanRoom(Model model
            , HttpServletRequest request
            , @ModelAttribute StompWomanRoomDto stompWomanRoomDto
    ) {
        try {
            if (stompWomanRoomDto == null) {
                return "redirect:/stompWoman/list";
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            String writer = cudInfoDto.getLoginUser().getNickname();
            StompWomanRoomDto stompWomanRoom = this.stompWomanRoomService.findByRoomId(stompWomanRoomDto.getId());
            if (this.getIndexOfWriter(writer, stompWomanRoom.getUserList()) >= 0) {
                log.error("{} 이미 존재하는 대화명", writer);
                return "redirect:/stompWoman/list";
            }
            model.addAttribute("stompWomanRoomDto", stompWomanRoom);
            model.addAttribute("writer", writer);
            String url = String.format("%s:%d", request.getServerName(), request.getServerPort());
            model.addAttribute("hostUrl", url);
            return "/stompWoman/stompWomanproomdetail";
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return "redirect:/";
        }
    }

    private int getIndexOfWriter(String writer, List<String> userList) {
        for (int i = 0; i < userList.size(); i++) {
            String user = userList.get(i);
            if (user.equals(writer)) {
                return i;
            }
        }
        return -1;
    }
}
