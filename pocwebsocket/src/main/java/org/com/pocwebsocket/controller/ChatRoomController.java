package org.com.pocwebsocket.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.com.pocwebsocket.dto.ChatRoomDto;
import org.com.pocwebsocket.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping
    public ResponseEntity<ChatRoomDto> createRoom(@RequestParam String name) {
        ChatRoomDto room = chatRoomService.createRoom(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // 채팅방 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomDto>> getAllRooms() {
        return ResponseEntity.ok(chatRoomService.findAllRooms());
    }

    // 특정 채팅방 조회
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomDto> getRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatRoomService.findRoomById(roomId));
    }

}
