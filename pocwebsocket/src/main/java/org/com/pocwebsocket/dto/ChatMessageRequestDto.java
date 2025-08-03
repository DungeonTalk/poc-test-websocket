package org.com.pocwebsocket.dto;

public record ChatMessageRequestDto (
    Long roomId,
    String sender,
    String message
) {}
