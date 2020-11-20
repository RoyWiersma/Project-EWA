package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("gp")
@DiscriminatorColumn(name = "type")
public class GP extends User {

    @Column(length = 45)
    private String speciality;

    @Column()
    private boolean isAdmin = false;

    @JsonBackReference
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public GP(String speciality, boolean isAdmin, List<Appointment> appointments) {
        this.speciality = speciality;
        this.isAdmin = isAdmin;
        this.appointments = appointments;
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
}
