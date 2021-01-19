package nl.infosupport2.zonneveld.noah;

import nl.infosupport2.zonneveld.controllers.AdminController;
import nl.infosupport2.zonneveld.repositories.ChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void clear() {

    }

    @Test
    void getAllGp() {

    }

    @Test
    void setAdmin() {

    }

    @Test
    void setRemoveAdmin() {

    }

    @Test
    void removeGp() {

    }
}
