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

    //chat details for GP
    @Query("SELECT c FROM Message AS m " +
            "LEFT JOIN Chat AS c ON m.chat.id = c.id " +
            "LEFT JOIN User AS u ON c.patient.id = u.id " +
            "WHERE c.doctor = ?1 " +
            "ORDER BY m.dateTime DESC")
    List<Chat> findChatByGP(GP gp);

    //chat details for Patient
    @Query("SELECT c FROM Message AS m " +
            "LEFT JOIN Chat AS c ON m.chat.id = c.id " +
            "LEFT JOIN User AS u ON c.doctor.id = u.id " +
            "WHERE c.patient = ?1 " +
            "ORDER BY m.dateTime DESC")
    List<Chat> findChatByPatient(Patient patient);

}
