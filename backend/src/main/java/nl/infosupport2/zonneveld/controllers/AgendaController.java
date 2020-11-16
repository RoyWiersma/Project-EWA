package nl.infosupport2.zonneveld.controllers;

import nl.infosupport2.zonneveld.entities.Appointment;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.AppointmentRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/agenda")
public class AgendaController {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AgendaController(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public Iterable<Appointment> getAllAppointments() {
        User user = userRepository.findById(1)
            .orElseThrow(() -> new ItemNotFoundException("Doktor niet gevonden"));

        return appointmentRepository.getAllByDoctor(user);
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Integer id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(String.format("De afspraak met id '%d' bestaat niet", id)));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> saveAppointment(@Valid @RequestBody Appointment appointment) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Afspraak is opgeslagen");
        response.put("appointment", appointmentRepository.save(appointment));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateAppointment(@PathVariable Integer id, @Valid @RequestBody Appointment editedAppointment) {
        Appointment newAppointment = appointmentRepository.findById(id)
            .map(appointment -> {
                appointment.setStart(editedAppointment.getStart());
                appointment.setEnd(editedAppointment.getEnd());
                appointment.setPatient(editedAppointment.getPatient());
                appointment.setDoctor(editedAppointment.getDoctor());
                appointment.setTitle(editedAppointment.getTitle());
                appointment.setDescription(editedAppointment.getDescription());
                appointment.setOnLocation(editedAppointment.isOnLocation());

                return appointmentRepository.save(appointment);
            })
            .orElseThrow(() -> new ItemNotFoundException(String.format("De afspraak met id '%d' bestaat niet", id)));

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", String.format("Afspraak '%s' is bewerkt", editedAppointment.getTitle()));
        response.put("appointment", newAppointment);

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteAppointment(@PathVariable Integer id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(String.format("De afspraak met id '%d' bestaat niet", id)));

        appointmentRepository.delete(appointment);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", String.format("Afsrpaak '%s' is verwijderd", appointment.getTitle()));

        return response;
    }
}
