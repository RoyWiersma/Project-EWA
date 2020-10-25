package nl.infosupport2.zonneveld.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class GP implements Serializable {

    @Id
    @OneToOne(optional = false)
    private User user;

    @Column(length = 45)
    private String speciality;

    public GP(User user, String speciality) {
        this.user = user;
        this.speciality = speciality;
    }

    public GP() { }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
