package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer Id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String function;

    @Column(nullable = false)
    private String manual;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToOne()
    private DeletedReason deletedReason;

    @OneToMany(mappedBy = "medicine")
    private Set<MedicineHasGPC> medicineHasGPCS = new HashSet<>();

    public Medicine(String name, String function, String manual, Boolean isDeleted, DeletedReason deletedReason) {
        this.name = name;
        this.function = function;
        this.manual = manual;
        this.isDeleted = isDeleted;
        this.deletedReason = deletedReason;
    }

    public Medicine() { }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getFunction() {
        return function;
    }

    public String getManual() {
        return manual;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public DeletedReason getDeletedReason() {
        return deletedReason;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setDeletedReason(DeletedReason deletedReason) {
        this.deletedReason = deletedReason;
    }

    public Set<MedicineHasGPC> getMedicineHasGPCS() {
        return medicineHasGPCS;
    }

    public void setMedicineHasGPCS(Set<MedicineHasGPC> medicineHasGPCS) {
        this.medicineHasGPCS = medicineHasGPCS;
    }

    public void addMedicineHasGPC(MedicineHasGPC medicineHasGPC) {
        this.medicineHasGPCS.add(medicineHasGPC);
    }
}
