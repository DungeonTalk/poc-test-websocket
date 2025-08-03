package org.com.pocwebsocket.service;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.com.pocwebsocket.dto.ChatRoomDto;
import org.com.pocwebsocket.entity.ChatRoom;
import org.com.pocwebsocket.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomDto createRoom(String name) {
        ChatRoom chatRoom = new ChatRoom(name);
        return ChatRoomDto.from(chatRoomRepository.save(chatRoom));
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDto> findAllRooms() {
        return chatRoomRepository.findAll().stream()
            .map(ChatRoomDto::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public ChatRoomDto findRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId)
            .map(ChatRoomDto::from)
            .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));
    }

}
