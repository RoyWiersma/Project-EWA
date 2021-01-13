package nl.infosupport2.zonneveld.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nl.infosupport2.zonneveld.entities.*;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.*;
import nl.infosupport2.zonneveld.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.FetchProfile;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private GPRepository gpRepository;
    @Autowired
    private ChatRepository chatRepository;

    @JsonView(UserView.PublicView.class)
    @GetMapping("")
    public Iterable<Patient> getPatientNames() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ItemNotFoundException("user niet gevonden"));
        if (user instanceof GP){
            GP gp = (GP) user;
            return gp.getPatients();
        }
        throw new ItemNotFoundException("user niet gevonden");
    }

    @GetMapping("/doctor")
    public Iterable<GP> getDoctorNames() throws UserPrincipalNotFoundException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserPrincipalNotFoundException(email));

        if (user instanceof Patient) {
            Patient patient = (Patient) user;
            return gpRepository.getGP(patient);
        } else
            throw new UserPrincipalNotFoundException(email);
    }

    @JsonView(UserView.DetailView.class)
    @GetMapping("/appointment")
    public Iterable<Appointment> getAppointmentsForDoctor() throws UserPrincipalNotFoundException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserPrincipalNotFoundException(email));

        if (user instanceof GP) {
            GP gp = (GP) user;
            return appointmentRepository.getAppointmentByDoctor(gp);
        } else if (user instanceof Patient) {
            Patient patient = (Patient) user;
            return appointmentRepository.getAppointmentByPatient(patient);
        } else
            throw new UserPrincipalNotFoundException(email);
    }

    @JsonView(UserView.PublicView.class)
    @GetMapping("/chat")
    public Iterable<Chat> getChatByGp() throws ItemNotFoundException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ItemNotFoundException("User niet gevonden"));
        if (user instanceof GP) {
            GP gp = (GP) user;
            return chatRepository.findChatByGP(gp);
        } else if (user instanceof Patient) {
            Patient patient = (Patient) user;
            System.out.println(chatRepository.findChatByPatient(patient));
            return chatRepository.findChatByPatient(patient);
            }  else throw new ItemNotFoundException("User niet gevonden");
    }
}
