package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(User.Type.GP)
@DiscriminatorColumn(name = "type")
public class GP extends User {

    @Column(length = 45)
    @JsonView(UserView.PublicView.class)
    private String speciality;

    @Column()
    @JsonView(UserView.PublicView.class)
    private boolean isAdmin = false;

    @JsonBackReference
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
    private List<Chat> chats;

    public GP(String speciality, boolean isAdmin, List<Appointment> appointments, List<Chat> chats) {
        this.speciality = speciality;
        this.isAdmin = isAdmin;
        this.appointments = appointments;
        this.chats = chats;
    }

    public GP() { }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
    }
}
