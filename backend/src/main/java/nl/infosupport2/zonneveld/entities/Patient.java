package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(User.Type.PATIENT)
@DiscriminatorColumn(name = "type")
public class Patient extends User {

    @JsonBackReference
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Appointment> appointments;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Chat> chats;

    public Patient() { }

    public Patient(List<Appointment> appointments, List<Chat> chats) {
        this.appointments = appointments;
        this.chats = chats;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
    }
}
