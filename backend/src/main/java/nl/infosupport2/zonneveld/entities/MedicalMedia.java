package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;

@Entity
public class MedicalMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer Id;

    @Column(nullable = false, unique = true, length = 45)
    private String Name;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String Data;

    @OneToOne(optional = false)
    private Dossier dossier;

    public MedicalMedia(String name, String data, Dossier dossier) {
        this.Name = name;
        this.Data = data;
        this.dossier = dossier;
    }

    public MedicalMedia() {}

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getData() {
        return Data;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setData(String data) {
        Data = data;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
}
