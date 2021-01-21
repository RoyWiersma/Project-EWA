package nl.infosupport2.zonneveld.justin;

import nl.infosupport2.zonneveld.entities.Chat;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.medical-media-directory=uploads/media")
public class ChatControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChatRepository chatRepository;

    private Patient testPatient;
    private GP testDoctor;

    @BeforeEach
    void setup() {
        testDoctor = new GP();
        testPatient = new Patient();
    }

    @Test
    void gets403WhenTryingToGetChatsByDoctorWithoutAuthentication() throws Exception {
        given(chatRepository.findByDoctor(testDoctor))
                .willReturn(List.of(new Chat("test-test-123", testDoctor, testPatient)));

        MockHttpServletResponse response = mvc.perform(
                get("/chat")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    void gets403WhenTryingToDeleteChatWithoutAuthentication() throws Exception {
        given(chatRepository.findByDoctor(testDoctor))
                .willReturn(List.of(new Chat("test-test-123", testDoctor, testPatient)));

        MockHttpServletResponse response = mvc.perform(
                delete("/chat/test-test-123")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }
}
