package nl.infosupport2.zonneveld.noah;

import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AdminRepositoryTest {
    @Autowired
    private GPRepository gpRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GPCRepository gpcRepository;

    private GP doctor1, doctor2;
    private Patient patient1, patient2, patient3, patient4;

    @BeforeEach
    void setup() {
        GPC gpc = new GPC("Test GPC", "test123", "1234AB", "test@test.nl", "123456789", "123456789", true);
        gpc = gpcRepository.save(gpc);

        doctor1 = new GP("TestD1", "Doctor", "de", "dokter@test.nl", "123455", "12312", gpc, "test", "test", true);
        doctor1 = gpRepository.save(doctor1);

        doctor2 = new GP("TestD2", "Doctor", "de", "dokter1@test.nl", "123485", "92312", gpc, "test", "test", true);
        doctor2 = gpRepository.save(doctor2);

        patient1 = new Patient("TestP1", "Patient", "de", "patient@test.nl", "1234422", "123123111", gpc, "abc", doctor1);
        patient1 = patientRepository.save(patient1);

        patient2 = new Patient("TestP2", "Patient", "de", "patient1@test.nl", "123433422", "12312113111", gpc, "abc", doctor1);
        patient2 = patientRepository.save(patient2);

        patient3 = new Patient("TestP3", "Patient", "de", "patient2@test.nl", "1233422", "123456", gpc, "abc", doctor2);
        patient3 = patientRepository.save(patient3);

        patient4 = new Patient("TestP4", "Patient", "de", "patient3@test.nl", "1236422", "127456", gpc, "abc", doctor2);
        patient4 = patientRepository.save(patient4);
    }

    @AfterEach
    void clear() {
        patientRepository.deleteAll();
        gpRepository.deleteAll();
        gpcRepository.deleteAll();
    }

    @Test
    void doesGetGPGiveCorrectDoctor() {
        assertEquals(doctor1.getId(), gpRepository.getGP(patient1).iterator().next().getId());
        assertEquals(doctor1.getId(), gpRepository.getGP(patient2).iterator().next().getId());
        assertEquals(doctor2.getId(), gpRepository.getGP(patient3).iterator().next().getId());
        assertEquals(doctor2.getId(), gpRepository.getGP(patient4).iterator().next().getId());

        assertNotEquals(doctor2.getId(), gpRepository.getGP(patient1).iterator().next().getId());
        assertNotEquals(doctor2.getId(), gpRepository.getGP(patient2).iterator().next().getId());
        assertNotEquals(doctor1.getId(), gpRepository.getGP(patient3).iterator().next().getId());
        assertNotEquals(doctor1.getId(), gpRepository.getGP(patient4).iterator().next().getId());
    }
}
