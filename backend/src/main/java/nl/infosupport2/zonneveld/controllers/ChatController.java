package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.entities.*;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.ChatRepository;
import nl.infosupport2.zonneveld.repositories.MessageRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatController(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @JsonView(UserView.ChatView.class)
    public List<Chat> getChats() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (user instanceof GP)
            return chatRepository.findByDoctor((GP) user);
        else if (user instanceof Patient)
            return chatRepository.findByPatient((Patient) user);

        return null;
    }

    @GetMapping("/{id}")
    @JsonView(UserView.ChatView.class)
    public List<Message> getChatById(@PathVariable String id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Chat met het id '%s bestaat niet", id)));

        return chat.getMessages();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createChat(@RequestBody Chat chat) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen recht om een chat aan te maken");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        chat.setId(UUID.randomUUID().toString());
        chat.setDoctor((GP) user);
        chatRepository.save(chat);

        response.put("success", true);
        response.put("chatId", chat.getId());
        response.put("message", "Chatruimte is aangemaakt");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/message")
    @JsonView(UserView.ChatView.class)
    public ResponseEntity<Map<String, Object>> postMessage(@RequestBody @Valid Message message, @PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Chat met het id '%s' bestaat niet", id)));

        message.setSender(user);
        message.setChat(chat);

        response.put("success", true);
        response.put("chatMessage", messageRepository.save(message));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteChat(@PathVariable String id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Chat met het id '%s' bestaat niet", id)));

        chatRepository.delete(chat);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Chat is verwijderd");

        return response;
    }
}
