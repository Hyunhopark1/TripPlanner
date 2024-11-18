package com.sb3.sbsj.stompWoman;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompWomanChatController {
    @Autowired
    private SimpMessageSendingOperations msgTempate;

    @Autowired
    private StompWomanRoomService stompWomanRoomService;

    @MessageMapping("/stompWoman/message")
    public void message(StompWomanMessageDto stompWomanMessageDto) {
        log.info("/stompWoman/message => id:{}, msgType:{}, writer:{}, message:{}"
                , stompWomanMessageDto.getId()
                , stompWomanMessageDto.getMsgType()
                , stompWomanMessageDto.getWriter()
                , stompWomanMessageDto.getMessage()
        );
        StompWomanRoomDto stompWomanRoom = stompWomanRoomService.findByRoomId(stompWomanMessageDto.getId());
        if (stompWomanRoom == null) {
            return;
        }
        if (StompWomanMessageDto.StompWomanMessageType.ENTER == stompWomanMessageDto.getMsgType()) {
            stompWomanRoom.getUserList().add(stompWomanMessageDto.getWriter());
        } else if (StompWomanMessageDto.StompWomanMessageType.OUT == stompWomanMessageDto.getMsgType()) {
            stompWomanRoom.getUserList().remove(stompWomanMessageDto.getWriter());
        }
        if (stompWomanRoom.getCount() < 1) {
            stompWomanRoomService.deleteByRoomId(stompWomanMessageDto.getId());
        } else {
            msgTempate.convertAndSend("/sub/stompWoman/room/" + stompWomanMessageDto.getId()
                    , stompWomanMessageDto);
        }
    }
}
