package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import net.bytebuddy.utility.RandomString;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.MedicalMedia;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import nl.infosupport2.zonneveld.repositories.MedicalMediaRepository;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import nl.infosupport2.zonneveld.services.UploadService;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gp")
public class GPController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final MedicalMediaRepository mediaRepository;
    private final GPRepository gpRepository;

    @Value("${app.medical-media-directory}")
    private String directory;

    @Autowired
    public GPController(UserRepository userRepository, PatientRepository patientRepository, MedicalMediaRepository mediaRepository, GPRepository gpRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.mediaRepository = mediaRepository;
        this.gpRepository = gpRepository;
    }

    @GetMapping("/patients")
    @JsonView(UserView.DetailView.class)
    public ResponseEntity<Map<String, Object>> getPatientsByDoctor() {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("success", true);
        response.put("patients", ((GP) user).getPatients());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    @JsonView(UserView.DetailView.class)
    public ResponseEntity<Map<String, Object>> getPatient(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("success", true);
        response.put("patient", patientRepository.findById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/patient/{id}/dossier")
    @JsonView(UserView.PublicView.class)
    public ResponseEntity<Map<String, Object>> getPatientDossier(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Patient niet gevonden"));

        response.put("success", true);
        response.put("dossier", patient.getDossier());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/patient/{id}/dossier", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @JsonView(UserView.PublicView.class)
    public ResponseEntity<Map<String, Object>> saveMedicalMediaToPatientDossier(
            @RequestParam("image") MultipartFile file, @RequestParam("description") String description,
            @PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (!(user instanceof GP)) {
            response.put("success", false);
            response.put("message", "U heeft geen rechten om deze request te doen");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String fileName = saveDossierMedia(file);
        if (null != fileName) {
            Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException("Patient niet gevonden"));

            MedicalMedia media = new MedicalMedia();
            media.setMedia(fileName);
            media.setDescription(description);
            media.setDossier(patient.getDossier());

            response.put("success", true);
            response.put("media", mediaRepository.save(media));

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        response.put("success", false);
        response.put("message", "Er is een fout ontstaan tijdens het opslaan van de foto");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Saves the medical image to the server and returns the filename
     *
     * @param file the file to be saved
     * @return the filename. If an error occurred null will be returned
     */
    private String saveDossierMedia(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = String.format("%s%s", RandomString.make(20), originalFileName.substring(originalFileName.indexOf('.')));

        try {
            UploadService.saveFile(directory, fileName, file);

            return fileName;
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getGPs() {
        Map<String, Object> response = new HashMap<>();
        response.put("gps", gpRepository.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getGP(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        response.put("gp", gpRepository.findById(id));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
