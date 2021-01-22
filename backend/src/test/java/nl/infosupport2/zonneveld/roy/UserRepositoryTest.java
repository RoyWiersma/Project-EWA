package nl.infosupport2.zonneveld.roy;

import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)

public class UserRepositoryTest {

    @Autowired // repository is automatically injected into the test instance
    private GPCRepository gpcRepository;
    @Autowired
    private GPRepository gpRepository;
    @Autowired
    private PatientRepository patientRepository;



    private GP doctor1, doctor2;
    private Patient patient1;

    @BeforeEach
    void setup() {
        GPC gpc = new GPC("Test GPC", "test123", "1234AB", "test@test.nl", "123456789", "123456789", true);
        gpc = gpcRepository.save(gpc);

        doctor1 = new GP("TestDocter", "Doctor", "de", "dokter@test.nl", "123455", "12312", gpc, "test", "test", true);
        doctor1 = gpRepository.save(doctor1);

        doctor2 = new GP("TestDocter2", "Doctor", "de", "dokter@test2.nl", "1234552", "123122", gpc, "test", "test", false);
        doctor2 = gpRepository.save(doctor2);

        patient1 = new Patient("TestPatient", "Patient", "de", "patient@test.nl", "1234422", "123123111", gpc, "abc", doctor1);
        patient1 = patientRepository.save(patient1);
    }

    @AfterEach
    void clearTables() {
        patientRepository.deleteAll();
        gpRepository.deleteAll();
        gpcRepository.deleteAll();
    }
    @Test
    void CheckifDocter1isAdmin() {
        assertTrue(doctor1.isAdmin());
    }
    @Test
    void CheckifDocter2isnotAdmin() {
        assertFalse(doctor2.isAdmin());
    }
    @Test
    void CheckifPatient1hasDoctor1() {
        assertEquals(doctor1, patient1.getDoctor());
    }

}
