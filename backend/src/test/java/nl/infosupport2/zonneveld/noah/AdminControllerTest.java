package nl.infosupport2.zonneveld.noah;

import nl.infosupport2.zonneveld.controllers.AdminController;
import nl.infosupport2.zonneveld.entities.Chat;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.medical-media-directory=uploads/media")
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
    void expect403WhenTryingToGetListOfGPWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/admin/get")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    void expect403WhenTryingToSetAdminWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                patch("/admin/setAdmin")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    void expect403WhenTryingToRemoveAdminWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                patch("/admin/setRemoveAdmin")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    void expect403WhenTryingToDeleteAdminWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                delete("/admin/deleteAdmin")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }
}
