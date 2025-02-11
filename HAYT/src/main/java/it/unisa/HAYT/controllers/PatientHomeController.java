package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientLoginDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientHomeController {

    @GetMapping("/patient-home")
    public String patientHome(Model model) {
        return "patient-home";
    }

}
