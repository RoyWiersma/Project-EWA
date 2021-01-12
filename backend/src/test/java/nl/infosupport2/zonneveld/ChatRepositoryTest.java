package nl.infosupport2.zonneveld;

import nl.infosupport2.zonneveld.entities.Chat;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.ChatRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private GPCRepository gpcRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GPRepository gpRepository;

    private GP doctor1, doctor2;
    private Patient patient1, patient2, patient3, patient4;

    @BeforeEach
    void setup() {
        GPC gpc = new GPC("Test GPC", "test123", "1234AB", "test@test.nl", "123456789", "123456789", true);
        gpc = gpcRepository.save(gpc);

        doctor1 = new GP("Test", "Doctor", "de", "dokter@test.nl", "123455", "12312", gpc, "test", "test", true);
        doctor1 = gpRepository.save(doctor1);

        doctor2 = new GP("Test1", "Doctor", "de", "dokter1@test.nl", "123485", "92312", gpc, "test", "test", true);
        doctor2 = gpRepository.save(doctor2);

        patient1 = new Patient("Test", "Patient", "de", "patient@test.nl", "1234422", "123123111", gpc, "abc", doctor1);
        patient1 = patientRepository.save(patient1);

        patient2 = new Patient("Test1", "Patient", "de", "patient1@test.nl", "123433422", "12312113111", gpc, "abc", doctor1);
        patient2 = patientRepository.save(patient2);

        patient3 = new Patient("Test2", "Patient", "de", "patient2@test.nl", "1233422", "123456", gpc, "abc", doctor1);
        patient3 = patientRepository.save(patient3);

        patient4 = new Patient("Test3", "Patient", "de", "patient3@test.nl", "1236422", "127456", gpc, "abc", doctor1);
        patient4 = patientRepository.save(patient4);

        Chat chat = new Chat("we112-e23qq", doctor1, patient1);
        Chat chat2 = new Chat("we11222-eqq", doctor1, patient2);
        Chat chat3 = new Chat("w123123e112-eqq", doctor1, patient3);

        chatRepository.saveAll(List.of(chat, chat2, chat3));
    }

    @AfterEach
    void clearTables() {
        chatRepository.deleteAll();
        patientRepository.deleteAll();
        gpRepository.deleteAll();
        gpcRepository.deleteAll();
    }

    @Test
    void doctor1ShouldHave3ChatRooms() {
        List<Chat> chats = chatRepository.findByDoctor(doctor1);

        assertEquals(3, chats.size());
    }

    @Test
    void doctor2ShouldHaveNoChatRooms() {
        List<Chat> chats = chatRepository.findByDoctor(doctor2);

        assertEquals(0, chats.size());
    }

    @Test
    void patient1ShouldHave1ChatRoom() {
        List<Chat> chats = chatRepository.findByPatient(patient1);

        assertEquals(1, chats.size());
    }

    @Test
    void patient4ShouldHaveNoChatRooms() {
        List<Chat> chats = chatRepository.findByPatient(patient4);

        assertEquals(0, chats.size());
    }
}
