package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.entities.Dossier;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.DossierRepository;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DossierRepository dossierRepository;

    private final JavaMailSender mailSender;

    @Autowired
    public PatientController(DossierRepository dossierRepository, PatientRepository patientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.dossierRepository = dossierRepository;
        this.mailSender = mailSender;
    }

    @GetMapping
    public Iterable<Patient> getPatients() {
        return patientRepository.findAll();
    }

    @PostMapping
    @JsonView(UserView.DetailView.class)
    public ResponseEntity<Map<String, Object>> savePatient(@RequestBody Patient patient) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String password = generateRandomPassword();
        StringBuilder name = new StringBuilder();

        GP doctor = (GP) user;
        patient.setDoctor(doctor);
        patient.setGpc(doctor.getGpc());
        patient.setPassword(passwordEncoder.encode(password));
        patient = patientRepository.save(patient);

        Dossier dossier = new Dossier();
        dossier.setPatient(patient);
        dossierRepository.save(dossier);

        name.append(String.format("%s ", patient.getFirstName()));
        if (patient.getMiddleName() != null)
            name.append(String.format("%s ", patient.getMiddleName()));
        name.append(patient.getLastName());

        sendConformationMail(patient.getEmail(), name.toString(), password);

        response.put("success", true);
        response.put("patient", patient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePatient(@RequestBody Patient editedPatient, @PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        Patient newPatient = patientRepository.findById(id)
                .map(patient -> {
                    patient.setFirstName(editedPatient.getFirstName());
                    patient.setMiddleName(editedPatient.getMiddleName());
                    patient.setLastName(editedPatient.getLastName());
                    patient.setMobileNumber(editedPatient.getMobileNumber());
                    patient.setPhoneNumber(editedPatient.getPhoneNumber());
                    patient.setEmail(editedPatient.getEmail());

                    return patientRepository.save(patient);
                })
                .orElseThrow(() -> new ItemNotFoundException(String.format("Patient met het id %d niet gevonden", id)));

        if (!(user instanceof GP) && !newPatient.getId().equals(editedPatient.getId())) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("success", true);
        response.put("patient", newPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePatient(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        patientRepository.deleteById(id);

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
