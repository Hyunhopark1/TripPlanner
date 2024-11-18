package com.sb3.sbsj.stompWoman;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stompWoman")
public class StompWomanRoomAjaxController implements IResponseController {
    @Autowired
    private StompWomanRoomService stompWomanRoomService;

    @Autowired
    private IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<StompWomanRoomDto> createStompWomanRoom(Model model, @RequestBody StompWomanRoomDto stompWomanRoomDto) {
        try {
            if (stompWomanRoomDto == null) {
                return ResponseEntity.badRequest().build();
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            StompWomanRoomDto newRoom = this.stompWomanRoomService.insert(stompWomanRoomDto.getRoomName());
            return ResponseEntity.ok(newRoom);
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
