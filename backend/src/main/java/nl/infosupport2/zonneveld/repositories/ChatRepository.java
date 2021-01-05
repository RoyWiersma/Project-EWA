package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.Chat;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, String> {

    @Query("SELECT c FROM Chat c WHERE c.doctor = ?1")
    List<Chat> findByDoctor(GP doctor);

    @Query("SELECT c FROM Chat c WHERE c.patient = ?1")
    List<Chat> findByPatient(Patient doctor);
}
