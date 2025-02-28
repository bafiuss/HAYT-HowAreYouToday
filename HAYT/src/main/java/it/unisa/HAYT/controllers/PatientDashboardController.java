package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PatientEntity;
import jakarta.servlet.http.HttpSession;
import it.unisa.HAYT.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PatientDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/patient-dashboard")
    public String showPatientDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpSession session) {
        if (userDetails != null) {
            String username = userDetails.getUsername();
            Optional<PatientEntity> patientOptional = userService.getPatient(username);

            if (patientOptional.isPresent()) {
                PatientEntity patient = patientOptional.get();
                model.addAttribute("patient", patient);

                boolean hasTherapist = patient.getPsychotherapist() != null;
                session.setAttribute("hasTherapist", hasTherapist);
            }
        }

        model.addAttribute("hideNavLinks", false);
        model.addAttribute("currentPage", "patient-dashboard");

        return "patient-dashboard";
    }


}
