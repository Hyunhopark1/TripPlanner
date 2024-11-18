package com.sb3.sbsj.stompWoman;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StompWomanMessageDto {
    public enum StompWomanMessageType {
        ENTER,
        MESSAGE,
        OUT,
    }

    private StompWomanMessageType msgType;
    private Long id;
    private String writer;
    private String message;
}
