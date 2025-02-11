package it.unisa.HAYT.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientLoginController {

    @GetMapping("/login")
    public String showPatientLoginPage(@RequestParam(value = "error", required = false) String error,
                                       Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if (error != null) {
                model.addAttribute("failedLogin", true); // Imposta il flag per l'errore
            }
            return "login";
        }

        model.addAttribute("hideNavLinks", false);
        return "index";
    }


}
