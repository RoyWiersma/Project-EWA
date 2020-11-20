package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
}
