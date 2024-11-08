package service_messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service_messaging.model.Conversation;
import service_messaging.model.Message;
import service_messaging.repository.ConversationRepository;
import service_messaging.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessagingService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    // Créer une nouvelle conversation
    public Conversation createConversation(Long senderId, Long receiverId) {
        Conversation conversation = new Conversation(senderId, receiverId);
        return conversationRepository.save(conversation);
    }

    // Envoyer un message dans une conversation
    public Message sendMessage(Long conversationId, String contenu) {
        Optional<Conversation> conversationOpt = conversationRepository.findById(conversationId);
        if (conversationOpt.isPresent()) {
            Conversation conversation = conversationOpt.get();
            Message message = new Message(conversation, contenu);
            return messageRepository.save(message);
        } else {
            throw new RuntimeException("Conversation not found");
        }
    }

    // Récupérer tous les messages d'une conversation
    public List<Message> getMessagesByConversationId(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    // Récupérer toutes les conversations d'un utilisateur
    public List<Conversation> getConversationsByUser(Long userId) {
        return conversationRepository.findBySenderIdOrReceiverId(userId, userId);
    }
}
