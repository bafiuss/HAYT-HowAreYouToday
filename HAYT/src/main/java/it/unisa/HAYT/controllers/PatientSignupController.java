package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String patientSignupPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            model.addAttribute("hideNavLinks", true);
            model.addAttribute("patientSignupDTO", new PatientSignupDTO());
            model.addAttribute("successSignup", false);
            model.addAttribute("failedSignup", false);

            return "signup";
        }

        model.addAttribute("hideNavLinks", false);

        return "index";
    }

    @PostMapping("/patient-signup")
    public String patientSignup(@Valid @ModelAttribute("patientSignupDTO") PatientSignupDTO patientSignupDTO,
                                Model model)
    {

        if(userService.emailAlreadyExists(patientSignupDTO.getEmail())) {
            model.addAttribute("failedSignup", true);
            return "signup";
        }

        userService.savePatient(patientSignupDTO);
        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientSignupDTO", new PatientSignupDTO());
        model.addAttribute("successSignup", true);

        return "signup";
    }

}
