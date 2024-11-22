package service_messaging.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;


@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonIgnore
    private Conversation conversation;

    private Long senderId;
    private String contenu;
    private LocalDateTime date;

    protected Message() {
    }

    public Message(Conversation conversation, Long senderId, String contenu) {
        this.conversation = conversation;
        this.senderId = senderId;
        this.contenu = contenu;
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public static Message createTestMessage(Long id, Long senderId, String contenu, LocalDateTime date) {
        Message message = new Message();
        message.setId(id);
        message.setSenderId(senderId);
        message.setContenu(contenu);
        message.setDate(date);
        return message;
    }
}
