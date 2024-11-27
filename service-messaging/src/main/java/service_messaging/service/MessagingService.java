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

    // Créer une nouvelle conversation entre deux personnes
    public Conversation createConversation(Long personOneId, Long personTwoId) {
        // Crée une nouvelle conversation avec personOneId et personTwoId
        Conversation conversation = new Conversation(personOneId, personTwoId);
        return conversationRepository.save(conversation);
    }

    /**
     * Get conversation by id
     * @param conversationId id of the conversation
     * @return conversation
     */
    public Conversation getConversationById(Long conversationId) {
        Optional<Conversation> conversationOpt = conversationRepository.findById(conversationId);
        if (conversationOpt.isPresent()) {
            return conversationOpt.get();
        } else {
            throw new RuntimeException("Conversation not found");
        }
    }

    // Envoyer un message dans une conversation existante
    public Message sendMessage(Long conversationId, Long senderId, String contenu) {
        Optional<Conversation> conversationOpt = conversationRepository.findById(conversationId);
        if (conversationOpt.isPresent()) {
            Conversation conversation = conversationOpt.get();
            // Crée un nouveau message avec senderId et contenu
            Message message = new Message(conversation, senderId, contenu);
            return messageRepository.save(message);
        } else {
            throw new RuntimeException("Conversation not found");
        }
    }

    // Récupérer tous les messages d'une conversation
    public List<Message> getMessagesByConversationId(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    // Récupérer toutes les conversations auxquelles un utilisateur a participé
    public List<Conversation> getConversationsByUser(Long userId) {
        return conversationRepository.findByPersonOneIdOrPersonTwoId(userId, userId);
    }
}
