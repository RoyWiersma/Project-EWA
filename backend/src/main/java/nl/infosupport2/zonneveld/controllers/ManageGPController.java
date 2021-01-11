package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.entities.Dossier;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.DossierRepository;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/gps")
public class ManageGPController {

    private final GPRepository gpRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    public ManageGPController(GPRepository gpRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, JavaMailSender mailSender) {
        this.gpRepository = gpRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @GetMapping
    public Iterable<GP> getAllGP() {
        return gpRepository.findAll();
    }

    @PostMapping
    @JsonView(UserView.DetailView.class)
    public ResponseEntity<Map<String, Object>> saveGP(@RequestBody GP doctor) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!((GP) user).isAdmin()){
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String password = generateRandomPassword();
        StringBuilder name = new StringBuilder();

        doctor.setPassword(passwordEncoder.encode(password));
        doctor.setGpc(user.getGpc());
        doctor = gpRepository.save(doctor);

        name.append(String.format("%s ", doctor.getFirstName()));
        if (doctor.getMiddleName() != null){
            name.append(String.format("%s ", doctor.getMiddleName()));
        }
        name.append(doctor.getLastName());

        sendConformationMail(doctor.getEmail(), name.toString(), password);

        response.put("success", true);
        response.put("doctor", doctor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateGP(@RequestBody GP editedGP, @PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        GP newGp = gpRepository.findById(id)
                .map(gp -> {
                    gp.setFirstName(editedGP.getFirstName());
                    gp.setMiddleName(editedGP.getMiddleName());
                    gp.setLastName(editedGP.getLastName());
                    gp.setMobileNumber(editedGP.getMobileNumber());
                    gp.setPhoneNumber(editedGP.getPhoneNumber());
                    gp.setEmail(editedGP.getEmail());

                    return gpRepository.save(gp);
                })
                .orElseThrow(() -> new ItemNotFoundException(String.format("Patient met het id %d niet gevonden", id)));


        if (!((GP) user).isAdmin() || !newGp.getId().equals(editedGP.getId())){
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("success", true);
        response.put("gp", newGp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteGP(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!((GP) user).isAdmin()){
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        gpRepository.deleteById(id);

        response.put("success", false);
        response.put("message", String.format("Patient met id %d verwijderd", id));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Sends a mail to the patient with there temporary login data
     *
     * @param to the email where the message is send to
     * @param name the name of the user
     * @param password the temporary password for the user
     */
    private void sendConformationMail(String to, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@zonneveld.nl");
        message.setTo(to);
        message.setSubject("Registatie Zonneveld");
        message.setText(
                String.format("Beste %s,\n\n Om in ons digitale systeem in te loggen, heeft u dit wachtwoord nodig %s\n\n Met vriendelijke groet, \n\n Zonneveld",
                        name, password)
        );

        mailSender.send(message);
    }

    /**
     * Generates a random 7 character string
     *
     * @return a random string
     */
    private String generateRandomPassword() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
