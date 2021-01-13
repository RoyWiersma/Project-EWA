package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chat {

    @Id
    @Column(nullable = false, unique = true)
    @JsonView(UserView.PublicView.class)
    private String id;

    @ManyToOne(optional = false)
    @JsonView(UserView.PublicView.class)
    private GP doctor;

    @ManyToOne(optional = false)
    @JsonView(UserView.PublicView.class)
    private Patient patient;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    @JsonView(UserView.PublicView.class)
    private List<Message> messages;

    public Chat(String id, GP doctor, Patient patient) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Chat() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(GP doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
