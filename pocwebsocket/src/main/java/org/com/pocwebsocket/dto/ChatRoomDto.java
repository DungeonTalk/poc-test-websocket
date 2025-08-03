package org.com.pocwebsocket.dto;

import org.com.pocwebsocket.entity.ChatRoom;

public record ChatRoomDto(
    Long id,
    String name
) {
    public static ChatRoomDto from(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom.getId(), chatRoom.getName());
    }
}
