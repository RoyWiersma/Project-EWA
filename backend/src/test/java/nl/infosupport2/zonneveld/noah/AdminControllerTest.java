package nl.infosupport2.zonneveld.noah;

import nl.infosupport2.zonneveld.controllers.AdminController;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private GPCRepository gpcRepository;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GPRepository repository;

    private GP doctor1, doctor2;

    @BeforeEach
    void setup() {
        GPC gpc = new GPC("Test GPC", "test123", "1234AB", "test@test.nl", "123456789", "123456789", true);
        gpc = gpcRepository.save(gpc);

        doctor1 = new GP("Test", "Doctor", "de", "dokter@test.nl", "123455", "12312", gpc, "test", "test", true);
        doctor1 = repository.save(doctor1);

        doctor2 = new GP("Test1", "Doctor", "de", "dokter1@test.nl", "123485", "92312", gpc, "test", "test", true);
        doctor2 = repository.save(doctor2);
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
        gpcRepository.deleteAll();
    }

    @Test
    void getAllGp() throws Exception {
        mvc.perform(get("/admin/get")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
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
