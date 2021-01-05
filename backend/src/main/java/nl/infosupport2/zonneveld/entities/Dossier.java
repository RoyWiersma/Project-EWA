package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer Id;

    @OneToOne(optional = false)
    private Patient patient;

    @ManyToMany
    @JoinTable(name = "dossier_has_medicine",
        joinColumns = { @JoinColumn(name = "medicine_id", nullable = false, updatable = false) },
        inverseJoinColumns = { @JoinColumn(name = "dossier_id", nullable = false, updatable = false) }
    )
    @JsonView(UserView.PublicView.class)
    private List<Medicine> medicines = new ArrayList<>();

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.REMOVE)
    @JsonView(UserView.PublicView.class)
    private List<MedicalMedia> media = new ArrayList<>();

    public Dossier(Patient patient) {
        this.patient = patient;
    }

    public Dossier() { }

    public Integer getId() {
        return Id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void addMedicines(Medicine medicine) {
        this.medicines.add(medicine);
    }

    public List<MedicalMedia> getMedia() {
        return media;
    }

    public void addMedia(MedicalMedia media) {
        this.media.add(media);
    }
}
