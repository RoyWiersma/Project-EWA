package nl.infosupport2.zonneveld.justin;

import nl.infosupport2.zonneveld.TokenManager;
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
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.BDDMockito.given;
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

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ChatRepository chatRepository;

    @Autowired
    private JacksonTester<Chat> jsonChat;

    private Patient testPatient;
    private GP testDoctor;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        testDoctor = new GP();
        testPatient = new Patient();
    }

    @Test
    void gets401WhenTryingToGetChatsByDoctor() throws Exception {
        given(chatRepository.findByDoctor(testDoctor))
                .willReturn(List.of(new Chat("test-test-123", testDoctor, testPatient)));

        MockHttpServletResponse response = mvc.perform(
                get("/chat")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    void gets200WhenTryingToGetChatsByDoctor() throws Exception {
        String token = TokenManager.generateToken("test@test.nl");

        given(chatRepository.findByDoctor(testDoctor))
                .willReturn(List.of(new Chat("test-test-123", testDoctor, testPatient)));

        MockHttpServletResponse response = mvc.perform(
                get("/chat")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
