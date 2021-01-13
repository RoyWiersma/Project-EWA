package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;

@Entity
public class MedicalMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JsonView(UserView.PublicView.class)
    private Integer id;

    @Column(nullable = false, unique = true, length = 45)
    @JsonView(UserView.DetailView.class)
    private String media;

    @Column(nullable = false)
    @JsonView(UserView.PublicView.class)
    private String description;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Dossier dossier;

    public MedicalMedia(String media, String description, Dossier dossier) {
        this.media = media;
        this.description = description;
        this.dossier = dossier;
    }

    public MedicalMedia() {}

    public Integer getId() {
        return id;
    }

    public String getMedia() {
        return media;
    }

    public String getDescription() {
        return description;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
}
