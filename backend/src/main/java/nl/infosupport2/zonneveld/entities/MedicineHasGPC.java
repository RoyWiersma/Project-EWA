package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class MedicineHasGPC {

    @EmbeddedId
    private MedicineHasGPCId id = new MedicineHasGPCId();

    @ManyToOne
    @MapsId("medicineId")
    private Medicine medicine;

    @ManyToOne
    @MapsId("GCPId")
    private GPC GPC;

    @Column(nullable = false)
    private Integer amount;

    public MedicineHasGPC(Medicine medicine, GPC GPC, Integer amount) {
        this.medicine = medicine;
        this.GPC = GPC;
        this.amount = amount;
    }

    public MedicineHasGPC() {

    }

    public Medicine getMedicine() {
        return medicine;
    }

    public GPC getGPC() {
        return GPC;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setGPCId(GPC GPC) {
        this.GPC = GPC;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Embeddable
    public static class MedicineHasGPCId implements Serializable {
        private static final long serialVersionID = 1L;

        private Integer medicineId;
        private Integer GCPId;

        public MedicineHasGPCId() { }

        public MedicineHasGPCId(Integer medicineId, Integer GCPId) {
            this.medicineId = medicineId;
            this.GCPId = GCPId;
        }

        public Integer getMedicineId() {
            return medicineId;
        }

        public void setMedicineId(Integer medicineId) {
            this.medicineId = medicineId;
        }

        public Integer getGCPId() {
            return GCPId;
        }

        public void setGCPId(Integer GCPId) {
            this.GCPId = GCPId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MedicineHasGPCId that = (MedicineHasGPCId) o;
            return Objects.equals(medicineId, that.medicineId) &&
                Objects.equals(GCPId, that.GCPId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(medicineId, GCPId);
        }
    }
}
