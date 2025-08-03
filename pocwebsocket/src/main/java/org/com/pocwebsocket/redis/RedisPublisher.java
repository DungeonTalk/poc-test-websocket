package org.com.pocwebsocket.redis;

import lombok.RequiredArgsConstructor;
import org.com.pocwebsocket.dto.ChatMessageResponseDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageResponseDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
