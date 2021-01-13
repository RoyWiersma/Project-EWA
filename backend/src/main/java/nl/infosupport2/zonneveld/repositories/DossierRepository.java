package nl.infosupport2.zonneveld.repositories;
import nl.infosupport2.zonneveld.entities.Dossier;
import nl.infosupport2.zonneveld.entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DossierRepository extends CrudRepository<Dossier, Integer> {
    @Query("SELECT new Patient(p.id, p.firstName, p.middleName, p.lastName, p.email, p.phoneNumber, p.mobileNumber, p.doctor) from Patient as p where p = ?1")
    Iterable<Dossier> getDossierByPatient(Patient patient);
}
