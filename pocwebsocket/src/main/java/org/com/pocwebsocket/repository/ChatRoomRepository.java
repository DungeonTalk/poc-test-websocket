package org.com.pocwebsocket.repository;

import java.util.Optional;
import org.com.pocwebsocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 채팅방 이름으로 중복 확인 등의 용도로 사용할 수 있음
    Optional<ChatRoom> findByName(String name);

    // 메시지 리스트 포함한 채팅방 조회 (N+1 방지)
    @EntityGraph(attributePaths = {"messages"})
    Optional<ChatRoom> findWithMessagesById(Long id);

}
