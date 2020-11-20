package nl.infosupport2.zonneveld.controllers;

import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.repositories.PatientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("")
    public Iterable<Patient> getPatients() {
        return patientRepository.findAll();
    }
}
