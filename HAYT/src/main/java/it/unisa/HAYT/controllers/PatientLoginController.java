package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientLoginDTO;
import it.unisa.HAYT.servicies.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String patientSignupPage(Model model) {
        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientLoginDTO", new PatientLoginDTO());
        model.addAttribute("success_login", false);
        model.addAttribute("failed_login", false);
        return "login";
    }

    @PostMapping("/patient-login")
    public String patientSignup(@Valid @ModelAttribute PatientLoginDTO patientLoginDTO, Model model) {

        if(!userService.userExists(patientLoginDTO.getEmail(),patientLoginDTO.getPassword())) {
            model.addAttribute("failed_login", true);
            model.addAttribute("hideNavLinks", true);
            return "login";
        }

        model.addAttribute("patientLoginDTO", new PatientLoginDTO());
        model.addAttribute("success_login", true);
        model.addAttribute("logged", true);

        return "patient-home";
    }

}
