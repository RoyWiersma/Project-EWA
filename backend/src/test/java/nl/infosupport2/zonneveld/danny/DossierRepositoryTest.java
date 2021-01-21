package nl.infosupport2.zonneveld.danny;

import nl.infosupport2.zonneveld.entities.Dossier;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.DossierRepository;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DossierRepositoryTest {
    @Autowired
    private GPCRepository gpcRepository;

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GPRepository gpRepository;

    private GP doctor1;
    private Patient patient;

    @Test
    void testAddingATask() {
        GPC gpc = new GPC("Test GPC", "test123", "1234AB", "test@test.nl", "123456789", "123456789", true);
        gpc = gpcRepository.save(gpc);

        doctor1 = new GP("Test", "Doctor", "de", "dokter@test.nl", "123455", "12312", gpc, "test", "test", true);
        doctor1 = gpRepository.save(doctor1);

        patient = new Patient("Test", "Patient", "de", "patient@test.nl", "1234422", "123123111", gpc, "abc", doctor1);
        patient = patientRepository.save(patient);

        // Arrange
        // declaring new task
        Dossier dossier = new Dossier(
           patient
        );

        // Act: saving task
        dossierRepository.save(dossier);

        // Assert: checks if data is correct
        //checks if id is not equal to 0
        assertNotNull(dossier.getId());

        // Act: find task by id
        Optional<Dossier> findDossier = dossierRepository.findById(dossier.getId());

        // Assert: checks if data is correct
        assertEquals(dossier.getId(), findDossier.get().getId());
        assertEquals(dossier.getPatient(), findDossier.get().getPatient());
    }

}
