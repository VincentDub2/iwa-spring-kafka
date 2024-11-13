package service_messaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service_messaging.model.Conversation;
import service_messaging.model.Message;
import service_messaging.service.MessagingService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessagingService messagingService;

    // Créer une nouvelle conversation entre deux utilisateurs
    @PostMapping("/conversation")
    public ResponseEntity<Conversation> createConversation(@RequestParam Long personOneId, @RequestParam Long personTwoId) {
        Conversation conversation = messagingService.createConversation(personOneId, personTwoId);
        return new ResponseEntity<>(conversation, HttpStatus.CREATED);
    }

    // Envoyer un message dans une conversation existante
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam Long conversationId, @RequestParam Long senderId, @RequestParam String contenu) {
        Message message = messagingService.sendMessage(conversationId, senderId, contenu);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Récupérer tous les messages d'une conversation
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long conversationId) {
        System.out.println("requete get conversationId = " + conversationId);
        List<Message> messages = messagingService.getMessagesByConversationId(conversationId);
        System.out.println("nb message trouvé = " + messages.size());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Récupérer toutes les conversations d'un utilisateur (personOneId ou personTwoId)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Conversation>> getConversationsByUser(@PathVariable Long userId) {
        List<Conversation> conversations = messagingService.getConversationsByUser(userId);
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }
}
