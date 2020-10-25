package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Message implements Serializable {

    @Id
    @OneToOne(optional = false)
    private Chat chat;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    private String media;

    @Id
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Id
    @OneToOne(optional = false)
    private User sender;

    public Message(Chat chat, String text, String media, LocalDateTime dateTime, User sender) {
        this.chat = chat;
        this.text = text;
        this.media = media;
        this.dateTime = dateTime;
        this.sender = sender;
    }

    public Message() { }

    public Chat getChat() {
        return chat;
    }

    public String getText() {
        return text;
    }

    public String getMedia() {
        return media;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public User getSender() {
        return sender;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

}
