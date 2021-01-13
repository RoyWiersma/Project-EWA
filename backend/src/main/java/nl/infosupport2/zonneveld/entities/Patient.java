package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(User.Type.PATIENT)
@DiscriminatorColumn(name = "type")
public class Patient extends User {

    @JsonView(UserView.DetailView.class)
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

    public Patient() {
    }

    public Patient(Integer id, String firstName, String lastName, String middleName, String email, String phoneNumber, String mobileNumber, GP doctor) {
        super(id, firstName, lastName, middleName, email, phoneNumber, mobileNumber);
        this.appointments = appointments;
        this.doctor = doctor;
    }

    public Patient(List<Appointment> appointments) {
        this.appointments = appointments;
    }

        public List<Appointment> getAppointments () {
            return appointments;
        }

        public void addAppointment (Appointment appointment){
            this.appointments.add(appointment);
        }

        public GP getDoctor () {
            return doctor;
        }

        public void setDoctor (GP doctor) {
            this.doctor = doctor;
        }
            public List<Chat> getChats () {
                return chats;
            }

            public void addChat (Chat chat){
                chats.add(chat);
            }

            public Dossier getDossier () {
                return dossier;
            }

            public void setDossier (Dossier dossier){
                this.dossier = dossier;

            }
        }

