package service_messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service_messaging.model.Conversation;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
