package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("gp")
@DiscriminatorColumn(name = "type")
public class GP extends User {

    @Column(length = 45)
    private String speciality;

    @Column()
    private boolean isAdmin = false;

    public GP(String speciality, boolean isAdmin) {
        this.speciality = speciality;
        this.isAdmin = isAdmin;
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
}
