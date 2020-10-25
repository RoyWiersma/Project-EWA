package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 200)
    private String lastName;

    @Column(length = 100)
    private String middleName;

    @Column(unique = true, length = 320)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(unique = true, length = 20)
    private String mobileNumber;

    @OneToOne(optional = false)
    private GPC gpc;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 32)
    private String salt;

    public User(String firstName, String lastName, String middleName, String email, String phoneNumber, String mobileNumber, GPC gpc, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.gpc = gpc;
        this.password = password;
        this.salt = salt;
    }

    public User() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public GPC getGpc() {
        return gpc;
    }

    public void setGpc(GPC gpc) {
        this.gpc = gpc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
