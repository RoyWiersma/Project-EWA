package nl.infosupport2.zonneveld.repositories;
import nl.infosupport2.zonneveld.entities.Appointment;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

    @Query(value = "SELECT a from Appointment a where a.doctor = ?1 order by a.start asc")
    Iterable<Appointment>getAppointmentByDoctor(GP gp);

    @Query("SELECT a from Appointment a where a.patient = ?1 order by a.start asc")
    Iterable<Appointment> getAppointmentByPatient(Patient patient);

}
