package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientLoginDTO;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping
@SessionAttributes("loggedUser")
public class PatientLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String patientSignupPage(Model model) {
        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientLoginDTO", new PatientLoginDTO());
        model.addAttribute("successLogin", false);
        model.addAttribute("failedLogin", false);

        return "login";
    }

    @PostMapping("/patient-login")
    public String patientSignup(@Valid @ModelAttribute("patientLoginDTO") PatientLoginDTO patientLoginDTO,
                                Model model,
                                HttpSession session)
    {
        String email = patientLoginDTO.getEmail();
        String password = patientLoginDTO.getPassword();

        if(!userService.userExists(email,password) ){
            model.addAttribute("failedLogin", true);
            return "login";
        }

        UserEntity user = userService.getUser(email);
        String role = userService.getUserRole(email);
        session.setAttribute("user", user);
        session.setAttribute("role", role);

        model.addAttribute("patientLoginDTO", new PatientLoginDTO());
        model.addAttribute("successLogin", true);
        model.addAttribute("hideNavLinks", false);

        return "patient-home";
    }

}
