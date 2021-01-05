package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(User.Type.PATIENT)
@DiscriminatorColumn(name = "type")
public class Patient extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    private GP doctor;

    @JsonBackReference
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Appointment> appointments;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Chat> chats;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private Dossier dossier;

    public Patient() { }

    public Patient(String firstName, String lastName, String middleName, String email, String phoneNumber,
                   String mobileNumber, GPC gpc, String password, GP doctor) {
        super(firstName, lastName, middleName, email, phoneNumber, mobileNumber, gpc, password);
        this.doctor = doctor;
    }

    public GP getDoctor() {
        return doctor;
    }

    public void setDoctor(GP doctor) {
        this.doctor = doctor;
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

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
}
