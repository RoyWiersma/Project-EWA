package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    private Chat chat;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Bericht is verplicht")
    @JsonView(UserView.PublicView.class)
    private String text;

    @JsonView(UserView.PublicView.class)
    private String media;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonView(UserView.PublicView.class)
    private LocalDateTime dateTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView(UserView.PublicView.class)
    private User sender;

    public Message(Chat chat, String text, String media, LocalDateTime dateTime, User sender) {
        this.chat = chat;
        this.text = text;
        this.media = media;
        this.dateTime = dateTime;
        this.sender = sender;
    }

    public Message() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
