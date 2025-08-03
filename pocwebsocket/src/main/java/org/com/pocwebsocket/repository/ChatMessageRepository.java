package org.com.pocwebsocket.repository;

import java.util.List;
import org.com.pocwebsocket.entity.ChatMessage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @EntityGraph(attributePaths = {"chatRoom"})
    List<ChatMessage> findAllByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);

}
