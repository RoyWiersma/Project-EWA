package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GPC {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 20)
    private String postalCode;

    @Column(unique = true, length = 320)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(unique = true, length = 20)
    private String mobileNumber;

    @Column(nullable = false)
    private boolean hasPharmacy = false;

    public GPC(String name, String address, String postalCode, String email, String phoneNumber, String mobileNumber, boolean hasPharmacy) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.hasPharmacy = hasPharmacy;
    }

    public GPC() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasPharmacy() {
        return hasPharmacy;
    }

    public void setHasPharmacy(boolean hasPharmacy) {
        this.hasPharmacy = hasPharmacy;
    }
}
