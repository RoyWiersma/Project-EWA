package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.entities.*;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.DossierRepository;
import nl.infosupport2.zonneveld.repositories.MedicalMediaRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import nl.infosupport2.zonneveld.services.UploadService;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dossier")
public class DossierController {

    private final UserRepository userRepository;
    private final MedicalMediaRepository medicalMediaRepository;
    private final DossierRepository dossierRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.medical-media-directory}")
    private String uploadDir;

    @Autowired
    public DossierController(UserRepository userRepository, MedicalMediaRepository medicalMediaRepository,
                             DossierRepository dossierRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.medicalMediaRepository = medicalMediaRepository;
        this.dossierRepository = dossierRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/medical-media/{id}")
    @JsonView(UserView.DetailView.class)
    public ResponseEntity<Map<String, Object>> getMedicalMedia(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            MedicalMedia media = medicalMediaRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(String.format("Media met id '%d' niet gevonden", id)));
            media.setMedia(UploadService.getFileUrl(uploadDir, media.getMedia()));

            response.put("success", true);
            response.put("media", media);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Er is een fout ontstaan tijdens het ophalen van de media");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medical-media/{id}")
    public ResponseEntity<Map<String, Object>> deleteMediaItem(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            MedicalMedia media = medicalMediaRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(String.format("Media met id '%d' niet gevonden", id)));

            if (!UploadService.deleteFile(uploadDir, media.getMedia())) {
                response.put("success", false);
                response.put("message", "Er is een fout ontstaan tijdens het verwijderen van de media");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            medicalMediaRepository.delete(media);

            response.put("success", true);
            response.put("message", String.format("Media item met id '%d' is verwijderd", id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Er is een fout ontstaan tijdens het ophalen van de media");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medical-media/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) throws IOException {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=\"%s\"", fileName))
                .body(UploadService.loadFile(uploadDir, fileName));
    }

    @JsonView(UserView.DetailView.class)
    @GetMapping("/patient")
    public Iterable<Dossier> getPatientDossier() throws UserPrincipalNotFoundException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserPrincipalNotFoundException(email));
        return dossierRepository.getDossierByPatient((Patient) user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User password) throws UserPrincipalNotFoundException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserPrincipalNotFoundException(email));

        if (user instanceof GP) {
            GP gp = (GP) user;
        } else if (user instanceof Patient) {
            Patient patient = (Patient) user;
            System.out.println(password.getPassword());

            if (passwordEncoder.matches(password.getPassword(), patient.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);

                return response;
            } else {
                throw new ItemNotFoundException("Verkeerde inloggegevens");
            }
        } else {
            throw new UserPrincipalNotFoundException(email);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("failed", false);
        response.put("message", "Er ging iets fout");

        return response;
    }
}
