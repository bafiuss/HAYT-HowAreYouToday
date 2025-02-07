package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        return "signup";
    }

    @PostMapping("/patient-signup")
    public String patientSignup(@Valid @ModelAttribute PatientSignupDTO patientSignupDTO, BindingResult result, Model model) {

        if(!patientSignupDTO.getPassword().equals(patientSignupDTO.getConfirmPassword())){
            result.addError(
                    new FieldError("patientSignupDTO", "confirmPassword", "Password and Confirm password do not match")
            );
        }

        if(userService.emailExists(patientSignupDTO.getEmail())) {
            result.addError(
                    new FieldError("patientSignupDTO", "email", "Email address is already used")
            );
        }

        if(result.hasErrors()) {
            return "signup";
        }

        userService.saveUser(patientSignupDTO);
        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientSignupDTO", new PatientSignupDTO());
        model.addAttribute("success_signup", true);

        return "signup";
    }


}
