package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(User.Type.GP)
@DiscriminatorColumn(name = "type")
public class GP extends User {

    @Column(length = 45)
    @JsonView(UserView.class)
    private String speciality;

    @Column()
    @JsonView(UserView.class)
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
