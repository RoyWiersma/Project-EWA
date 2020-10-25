package nl.infosupport2.zonneveld.controllers;

import nl.infosupport2.zonneveld.entities.GPC;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "")
    public User addNewUser() {
        GPC gpc = new GPC("De test praktijk", "Test straat 1", "1234 AB", "test@test.nl", "0229 274 006", null, false);
        User user = new User("Test", "Gebruiker", "de", "Visstick@test.nl", null, "06 22 83 03 82", gpc, "Vissticks", "de");
        userRepository.save(user);

        return user;
    }
}
