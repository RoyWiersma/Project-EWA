package nl.infosupport2.zonneveld.controllers;

import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return repository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(String.format("De gebruiker met id '%d' bestaat niet", id)));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        User newUser = user;
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(newUser);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Uw account is aangemaakt");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
