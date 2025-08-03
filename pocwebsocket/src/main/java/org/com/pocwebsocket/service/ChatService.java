package org.com.pocwebsocket.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.com.pocwebsocket.dto.ChatMessageRequestDto;
import org.com.pocwebsocket.dto.ChatMessageResponseDto;
import org.com.pocwebsocket.dto.ChatRoomResponseDto;
import org.com.pocwebsocket.entity.ChatMessage;
import org.com.pocwebsocket.entity.ChatRoom;
import org.com.pocwebsocket.redis.RedisPublisher;
import org.com.pocwebsocket.repository.ChatMessageRepository;
import org.com.pocwebsocket.repository.ChatRoomRepository;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisPublisher redisPublisher;
    private final ChannelTopic topic = new ChannelTopic("chat");

    public void handleChatMessage(ChatMessageRequestDto requestDto) {
        ChatRoom room = chatRoomRepository.findById(requestDto.roomId())
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        // 메시지 저장
        ChatMessage savedMessage = ChatMessage.builder()
            .sender(requestDto.sender())
            .message(requestDto.message().trim())
            .chatRoom(room)
            .build();

        chatMessageRepository.save(savedMessage);

        // 클라이언트에게 전송할 메시지 구성
        ChatMessageResponseDto responseDto = new ChatMessageResponseDto(
            savedMessage.getId(),
            savedMessage.getSender(),
            savedMessage.getMessage(),
            savedMessage.getCreatedAt(),
            room.getId()
        );

        // Redis Pub/Sub 발행
        redisPublisher.publish(topic, responseDto);
    }

    public ChatRoomResponseDto createRoom(String name) {
        ChatRoom room = ChatRoom.builder()
            .name(name)
            .build();

        ChatRoom savedRoom = chatRoomRepository.save(room);

        return new ChatRoomResponseDto(savedRoom.getId(), savedRoom.getName());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getAllRooms() {
        return chatRoomRepository.findAll().stream()
            .map(room -> new ChatRoomResponseDto(room.getId(), room.getName()))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getMessagesByRoomId(Long roomId) {
        return chatMessageRepository.findAllByChatRoomIdOrderByCreatedAtAsc(roomId)
            .stream()
            .map(message -> new ChatMessageResponseDto(
                message.getId(),
                message.getSender(),
                message.getMessage(),
                message.getCreatedAt(),
                message.getChatRoom().getId()
            ))
            .toList();
    }

}
