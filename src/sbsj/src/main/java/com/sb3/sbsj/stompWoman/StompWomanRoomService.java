package com.sb3.sbsj.stompWoman;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StompWomanRoomService {
    private final Map<Long, StompWomanRoomDto> stompWomanRoomDtoMap = new LinkedHashMap<>();

    public StompWomanRoomDto insert(String roomName) {
        StompWomanRoomDto newRoom = StompWomanRoomDto.builder()
                .id(0L)
                .roomName(roomName)
                .userList(new ArrayList<>())
                .build();
        stompWomanRoomDtoMap.put(newRoom.getId(), newRoom);
        return newRoom;
    }

    public StompWomanRoomDto findByRoomId(Long id) {
        return stompWomanRoomDtoMap.get(id);
    }

    public List<StompWomanRoomDto> findAll() {
        return stompWomanRoomDtoMap.values().stream().toList();
    }

    public void deleteByRoomId(Long id) {
        stompWomanRoomDtoMap.remove(id);
    }
}
