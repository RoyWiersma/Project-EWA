package nl.infosupport2.zonneveld.entities;

import javax.persistence.*;

@Entity
public class DeletedReason {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer Id;

    @Column(nullable = false)
    private String name;

    public DeletedReason(String name) {
        this.name = name;
    }

    public DeletedReason() { }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
