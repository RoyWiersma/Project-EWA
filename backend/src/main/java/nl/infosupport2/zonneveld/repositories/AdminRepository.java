package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User AS u WHERE u.Type = 'gp'")
    Optional<User> GetAllGp(String email);

    @Query("SELECT u FROM User AS u WHERE u. = 0 AND u.Type = 'gp'")
    Optional<User> GetNoAdminGp(String email);

    @Query("SELECT u FROM User AS u WHERE u. = 0 AND u.Type = 'gp'")
    Optional<User> GetAllAdminGp(String email);
}
