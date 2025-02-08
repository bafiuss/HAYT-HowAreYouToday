package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PatientSignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String patientSignupPage(HttpServletRequest request, Model model) {
        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientSignupDTO", new PatientSignupDTO());
        model.addAttribute("success_signup", false);
        model.addAttribute("failed_signup", false);
        return "signup";
    }

    @PostMapping("/patient-signup")
    public String patientSignup(@Valid @ModelAttribute PatientSignupDTO patientSignupDTO, Model model) {

        if(userService.emailExists(patientSignupDTO.getEmail())) {
            model.addAttribute("failed_signup", true);
            return "signup";
        }

        userService.saveUser(patientSignupDTO);
        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientSignupDTO", new PatientSignupDTO());
        model.addAttribute("success_signup", true);

        return "signup";
    }

}
