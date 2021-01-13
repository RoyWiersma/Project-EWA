package nl.infosupport2.zonneveld.repositories;
import nl.infosupport2.zonneveld.entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    @Query("SELECT p.id, p.firstName, p.lastName, p.email, p.middleName, p.password, p.phoneNumber, p.mobileNumber FROM Patient AS p")
    Iterable<Patient> getAllPatients();

}



