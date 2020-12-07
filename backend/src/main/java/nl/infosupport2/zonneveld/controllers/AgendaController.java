package nl.infosupport2.zonneveld.controllers;

import nl.infosupport2.zonneveld.entities.Appointment;
import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.entities.Patient;
import nl.infosupport2.zonneveld.entities.User;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.AppointmentRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping
    public List<Appointment> getAllAppointments() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        if (user instanceof GP)
            return ((GP) user).getAppointments();
        else if (user instanceof Patient)
            return ((Patient) user).getAppointments();
        else
            throw new ItemNotFoundException("Gebruiker niet gevonen");
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Integer id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(String.format("De afspraak met id '%d' bestaat niet", id)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveAppointment(@Valid @RequestBody Appointment appointment) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

            if (user instanceof Patient)
                appointment.setPatient((Patient) user);
            if (user instanceof GP)
                appointment.setDoctor((GP) user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Afspraak is opgeslagen");
            response.put("appointment", appointmentRepository.save(appointment));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateAppointment(@PathVariable Integer id, @Valid @RequestBody Appointment editedAppointment) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Gebruiker niet gevonen"));

        Appointment newAppointment = appointmentRepository.findById(id)
            .map(appointment -> {
                if (user instanceof Patient)
                    appointment.setDoctor(editedAppointment.getDoctor());
                if (user instanceof GP)
                    appointment.setPatient(editedAppointment.getPatient());

                appointment.setStart(editedAppointment.getStart());
                appointment.setEnd(editedAppointment.getEnd());
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
