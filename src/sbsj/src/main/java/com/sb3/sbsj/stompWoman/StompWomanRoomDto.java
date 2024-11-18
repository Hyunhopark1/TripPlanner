package com.sb3.sbsj.stompWoman;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StompWomanRoomDto {
    private String roomName;
    private Long id;
    private List<String> userList;

    public Integer getCount() {
        return userList.size();
    }

    ;
}
