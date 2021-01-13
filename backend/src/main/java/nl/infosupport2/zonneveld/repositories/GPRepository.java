package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.GP;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;

public interface GPRepository extends CrudRepository<GP, Integer> {

    @Query("SELECT gp.id, gp.firstName, gp.middleName, gp.lastName FROM Patient AS pa LEFT JOIN User AS gp ON pa.doctor = gp WHERE pa = ?1")
    Iterable<GP> getGP(Patient patient);
}


