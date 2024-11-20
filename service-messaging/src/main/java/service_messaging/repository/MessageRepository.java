package service_messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service_messaging.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Trouver les messages en fonction de l'identifiant de la conversation
    List<Message> findByConversationId(Long conversationId);
}
