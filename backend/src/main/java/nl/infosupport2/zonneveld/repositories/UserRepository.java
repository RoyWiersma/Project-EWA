package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
