package nl.infosupport2.zonneveld.repositories;

import nl.infosupport2.zonneveld.entities.GP;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GPRepository extends CrudRepository<GP, Integer> {

}