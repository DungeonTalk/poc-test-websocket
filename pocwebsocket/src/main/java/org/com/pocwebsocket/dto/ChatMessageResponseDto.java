package org.com.pocwebsocket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatMessageResponseDto(
    Long id,
    String sender,
    String message,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,

    Long roomId
) {
    public ChatMessageResponseDto {
        message = message.trim();
    }
}
