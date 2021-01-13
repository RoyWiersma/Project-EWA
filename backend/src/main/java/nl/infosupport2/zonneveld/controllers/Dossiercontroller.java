package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.MedicalMedia;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dossier")
public class DossierController {

    private final UserRepository userRepository;
    private final MedicalMediaRepository medicalMediaRepository;

    @Value("${app.medical-media-directory}")
    private String uploadDir;

    @Autowired
    public DossierController(UserRepository userRepository, MedicalMediaRepository medicalMediaRepository) {
        this.userRepository = userRepository;
        this.medicalMediaRepository = medicalMediaRepository;
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
}
