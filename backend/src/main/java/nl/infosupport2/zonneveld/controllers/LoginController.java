package nl.infosupport2.zonneveld.controllers;

import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    public static class LoginData{
        private String email;
        private String password;

        public LoginData(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public LoginData() {
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    @Autowired
    private UserRepository repository;

    @PostMapping("")
    public Optional<User> login(@RequestBody LoginData loginData) {
        return repository.findUserByEmail(loginData.getEmail());
    }
}

