package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.Appointment;
import nl.infosupport2.zonneveld.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}
