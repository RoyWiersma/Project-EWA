package nl.infosupport2.zonneveld.controllers;
import nl.infosupport2.zonneveld.entities.Appointment;
import nl.infosupport2.zonneveld.repositories.AppointmentRepository;
import nl.infosupport2.zonneveld.repositories.GPCRepository;
import nl.infosupport2.zonneveld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;

    @GetMapping
    public Iterable<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

}
