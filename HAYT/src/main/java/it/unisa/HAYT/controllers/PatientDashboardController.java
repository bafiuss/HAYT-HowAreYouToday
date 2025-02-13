package it.unisa.HAYT.controllers;

//import it.unisa.HAYT.dto.PatientLoginDTO;
import it.unisa.HAYT.entities.UserEntity;
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
    public String showPatientDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            String username = userDetails.getUsername();
            Optional<UserEntity> userOptional = userService.getUser(username);
            userOptional.ifPresent(userEntity -> model.addAttribute("user", userEntity));
        }
        model.addAttribute("hideNavLinks", false);

        return "patient-dashboard";
    }

}
