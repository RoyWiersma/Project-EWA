package nl.infosupport2.zonneveld.entities;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.views.UserView;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class User {

    public static class Type {
        public static final String GP = "gp";
        public static final String PATIENT = "patient";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JsonView(UserView.ChatView.class)
    private Integer id;

    @Column(nullable = false, length = 100)
    @JsonView(UserView.PublicView.class)
    private String firstName;

    @Column(nullable = false, length = 200)
    @JsonView(UserView.PublicView.class)
    private String lastName;

    @Column(length = 100)
    @JsonView(UserView.PublicView.class)
    private String middleName;

    @Column(unique = true, length = 320)
    @JsonView(UserView.PublicView.class)
    private String email;

    @Column(unique = true, length = 20)
    @JsonView(UserView.PublicView.class)
    private String phoneNumber;

    @Column(unique = true, length = 20)
    @JsonView(UserView.PublicView.class)
    private String mobileNumber;

    @ManyToOne(optional = false)
    private GPC gpc;

    @Column(nullable = false)
    private String password;

    public User(Integer id, String firstName, String lastName, String middleName, String email, String phoneNumber, String mobileNumber, GPC gpc, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.gpc = gpc;
        this.password = password;
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
}
