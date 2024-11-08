package service_messaging.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service_messaging.model.Conversation;
import service_messaging.model.Message;
import service_messaging.repository.ConversationRepository;
import service_messaging.repository.MessageRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessagingServiceTest {

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessagingService messagingService;

    @Test
    public void testCreateConversation() {
        Long senderId = 1L;
        Long receiverId = 2L;
        Conversation conversation = new Conversation(senderId, receiverId);
        
        when(conversationRepository.save(conversation)).thenReturn(conversation);

        Conversation createdConversation = messagingService.createConversation(senderId, receiverId);
        assertNotNull(createdConversation);
        assertEquals(senderId, createdConversation.getSenderId());
    }

    @Test
    public void testSendMessage() {
        Long conversationId = 1L;
        String contenu = "Hello, how are you?";
        Conversation conversation = new Conversation(1L, 2L);
        Message message = new Message(conversation, contenu);
        
        when(conversationRepository.findById(conversationId)).thenReturn(Optional.of(conversation));
        when(messageRepository.save(message)).thenReturn(message);

        Message sentMessage = messagingService.sendMessage(conversationId, contenu);
        assertNotNull(sentMessage);
        assertEquals(contenu, sentMessage.getContenu());
    }
}
