package service_messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service_messaging.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationId(Long conversationId);
}
