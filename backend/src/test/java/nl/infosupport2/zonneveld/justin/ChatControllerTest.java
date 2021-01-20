package nl.infosupport2.zonneveld.justin;

import nl.infosupport2.zonneveld.controllers.ChatController;
import nl.infosupport2.zonneveld.entities.Chat;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureJsonTesters
@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChatRepository chatRepository;

    @Autowired
    private JacksonTester<Chat> jsonChat;

    private Patient testPatient;
    private GP testDoctor;

    @BeforeEach
    void globalSetup() {
        testPatient = new Patient();
        testPatient.setId(1);
        testPatient.setFirstName("Test");
        testPatient.setLastName("Patient");

        testDoctor = new GP();
        testDoctor.setId(2);
        testDoctor.setFirstName("Test");
        testDoctor.setLastName("Doctor");
    }

    @Test
    void canRetrieveChatByDoctor() throws Exception {
        given(chatRepository.findByDoctor(testDoctor))
                .willReturn(List.of(new Chat("test-test-123", testDoctor, testPatient)));

        MockHttpServletResponse response = mvc.perform(
                get("/chat")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(),
                jsonChat.write(new Chat("test-test-123", testDoctor, testPatient)).getJson());
    }
}
