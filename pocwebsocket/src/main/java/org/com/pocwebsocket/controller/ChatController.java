package org.com.pocwebsocket.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.com.pocwebsocket.dto.ChatMessageRequestDto;
import org.com.pocwebsocket.dto.ChatMessageResponseDto;
import org.com.pocwebsocket.dto.ChatRoomResponseDto;
import org.com.pocwebsocket.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    // STOMP 메시지 수신 엔드포인트 (/pub/chat/message)
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessageRequestDto requestDto) {
        chatService.handleChatMessage(requestDto);
    }

    // ✅ 특정 채팅방의 메시지 조회
    @GetMapping("/messages")
    public List<ChatMessageResponseDto> getMessages(@RequestParam Long roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }

}
