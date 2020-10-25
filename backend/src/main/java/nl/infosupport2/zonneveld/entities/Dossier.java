package nl.infosupport2.zonneveld.entities;

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
    private User user;

    @ManyToMany
    @JoinTable(name = "dossier_has_medicine",
        joinColumns = { @JoinColumn(name = "medicine_id", nullable = false, updatable = false) },
        inverseJoinColumns = { @JoinColumn(name = "dossier_id", nullable = false, updatable = false) }
    )
    private List<Medicine> medicines = new ArrayList<>();

    public Dossier(User user) {
        this.user = user;
    }

    public Dossier() { }

    public Integer getId() {
        return Id;
    }

    public User getUserId() {
        return user;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
