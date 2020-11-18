package nl.infosupport2.zonneveld.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "patient")
@DiscriminatorColumn(name = "type")
public class Patient extends User {

    public Patient() { }

}
