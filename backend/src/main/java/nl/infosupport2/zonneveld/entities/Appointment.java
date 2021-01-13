package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JsonView(UserView.PublicView.class)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Begin datum van de afspraak is verplicht")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonView(UserView.PublicView.class)
    private LocalDateTime start;

    @Column(nullable = false)
    @NotNull(message = "Eind datum van de afspraak is verplicht")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonView(UserView.PublicView.class)
    private LocalDateTime end;

    @ManyToOne(optional = false)
    @JsonView(UserView.PublicView.class)
    private GP doctor;

    @ManyToOne(optional = false)
    @JsonView(UserView.PublicView.class)
    private Patient patient;

    @Column(nullable = false, length = 45)
    @NotEmpty(message = "Titel voor afspraak is verplicht")
    @JsonView(UserView.PublicView.class)
    private String title;

    @Column(columnDefinition = "TEXT")
    @JsonView(UserView.DetailView.class)
    private String description;

    @Column(nullable = false)
    @JsonView(UserView.PublicView.class)
    private boolean onLocation = false;

    public Appointment() {}

    public Appointment(LocalDateTime start, LocalDateTime end, GP doctor, Patient patient, String title, String description) {
        this.start = start;
        this.end = end;
        this.doctor = doctor;
        this.patient = patient;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public GP getDoctor() {
        return doctor;
    }

    public void setDoctor(GP doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnLocation() {
        return onLocation;
    }

    public void setOnLocation(boolean onLocation) {
        this.onLocation = onLocation;
    }
}

