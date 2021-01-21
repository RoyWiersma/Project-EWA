package nl.infosupport2.zonneveld.danny;

import nl.infosupport2.zonneveld.entities.Dossier;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.DossierRepository;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DossierRepositoryTest {
    //Repositories autowired in order to use them
    @Autowired
    private GPCRepository gpcRepository;

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GPRepository gpRepository;

    //Attributes
    private GP doctor1;
    private Patient patient;
    private Dossier dossier;

    //Setup before each test
    @BeforeEach
    void setUp() {

        GPC gpc = new GPC("Test GPC", "test123", "1234AB", "test@test.nl", "123456789", "123456789", true);
        gpc = gpcRepository.save(gpc);

        doctor1 = new GP("Test", "Doctor", "de", "dokter@test.nl", "123455", "12312", gpc, "test", "test", true);
        doctor1 = gpRepository.save(doctor1);

        patient = new Patient("Test", "Patient", "de", "patient@test.nl", "1234422", "123123111", gpc, "abc", doctor1);
        patient = patientRepository.save(patient);

        dossier = new Dossier(patient);
        dossier = dossierRepository.save(dossier);
    }

    //Delete all raw data from repositories after test
    @AfterEach
    void clear() {
        dossierRepository.deleteAll();
        patientRepository.deleteAll();
        gpRepository.deleteAll();
        gpcRepository.deleteAll();
    }

    @Test
    void checkIfDossiersHaveTheSameId() {
        // Iterate through dossier where patient is inserted patient
        Dossier dossier1 = dossierRepository.getDossierByPatient(patient).iterator().next();
        // Compare dossierId {dossier1} with {dossier} as declared in setUp
        assertEquals(dossier1.getPatient().getId(), dossier.getPatient().getId());
    }

}
