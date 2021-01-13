package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User AS u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);

}
