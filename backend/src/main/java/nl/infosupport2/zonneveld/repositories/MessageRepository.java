package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
