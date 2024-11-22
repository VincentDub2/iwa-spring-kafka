package service_messaging.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service_messaging.model.Conversation;
import service_messaging.model.Message;
import service_messaging.service.MessagingService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessagingService messagingService;

    @Test
    void testSendMessage() throws Exception {
        Message mockMessage = Message.createTestMessage(1L, 1L, "Bonjour", LocalDateTime.now());

        mockMessage.setId(1L);
        mockMessage.setSenderId(1L);
        mockMessage.setContenu("Bonjour");
        mockMessage.setDate(LocalDateTime.parse("2024-11-21T12:00:00", DateTimeFormatter.ISO_DATE_TIME));

        Mockito.when(messagingService.sendMessage(anyLong(), anyLong(), anyString()))
            .thenReturn(mockMessage);

        mockMvc.perform(post("/send")
                .param("conversationId", "1")
                .param("senderId", "1")
                .param("contenu", "Bonjour")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.senderId").value(1))
            .andExpect(jsonPath("$.contenu").value("Bonjour"))
            .andExpect(jsonPath("$.date").value("2024-11-21T12:00:00"));

        Mockito.verify(messagingService).sendMessage(1L, 1L, "Bonjour");
    }

    @Test
    void testGetMessages() throws Exception {
        LocalDateTime date1 = LocalDateTime.parse("2024-11-13T10:00:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime date2 = LocalDateTime.parse("2024-11-13T10:05:00", DateTimeFormatter.ISO_DATE_TIME);
        
        Message message1 = Mockito.mock(Message.class);
        Mockito.when(message1.getId()).thenReturn(1L);
        Mockito.when(message1.getContenu()).thenReturn("Bonjour");
        Mockito.when(message1.getDate()).thenReturn(date1);

        Message message2 = Mockito.mock(Message.class);
        Mockito.when(message2.getId()).thenReturn(2L);
        Mockito.when(message2.getContenu()).thenReturn("Très bien, merci");
        Mockito.when(message2.getDate()).thenReturn(date2);

        Mockito.when(messagingService.getMessagesByConversationId(1L))
            .thenReturn(Arrays.asList(message1, message2));

        mockMvc.perform(get("/conversation/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].contenu").value("Bonjour"))
            .andExpect(jsonPath("$[0].date").value("2024-11-13T10:00:00"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].contenu").value("Très bien, merci"))
            .andExpect(jsonPath("$[1].date").value("2024-11-13T10:05:00"));

        Mockito.verify(messagingService).getMessagesByConversationId(1L);
    }

    @Test
    void testGetConversationsByUser() throws Exception {
        Conversation conversation = Conversation.createTestConversation(666L, 42L, 52L, new ArrayList<Message>());
        conversation.setId(1L);
        conversation.setPersonOneId(1L);
        conversation.setPersonTwoId(2L);

        Mockito.when(messagingService.getConversationsByUser(1L))
            .thenReturn(Collections.singletonList(conversation));

        mockMvc.perform(get("/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].personOneId").value(1))
            .andExpect(jsonPath("$[0].personTwoId").value(2));

        Mockito.verify(messagingService).getConversationsByUser(1L);
    }

    @Test
    void testCreateConversation() throws Exception {
        Conversation conversation = Conversation.createTestConversation(666L, 42L, 52L, new ArrayList<Message>());
        conversation.setId(1L);
        conversation.setPersonOneId(1L);
        conversation.setPersonTwoId(2L);

        Mockito.when(messagingService.createConversation(1L, 2L))
            .thenReturn(conversation);

        mockMvc.perform(post("/conversation")
                .param("personOneId", "1")
                .param("personTwoId", "2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.personOneId").value(1))
            .andExpect(jsonPath("$.personTwoId").value(2));

        Mockito.verify(messagingService).createConversation(1L, 2L);
    }

}
