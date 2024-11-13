package service_messaging.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long personOneId;
    private Long personTwoId;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    protected Conversation() {
    }

    public Conversation(Long personOneId, Long personTwoId) {
        this.personOneId = personOneId;
        this.personTwoId = personTwoId;
    }

    public Long getId() {
        return id;
    }

    public Long getPersonOneId() {
        return personOneId;
    }

    public void setPersonOneId(Long personOneId) {
        this.personOneId = personOneId;
    }

    public Long getPersonTwoId() {
        return personTwoId;
    }

    public void setPersonTwoId(Long personTwoId) {
        this.personTwoId = personTwoId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
