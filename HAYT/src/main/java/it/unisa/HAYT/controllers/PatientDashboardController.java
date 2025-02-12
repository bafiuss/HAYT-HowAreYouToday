package it.unisa.HAYT.controllers;

//import it.unisa.HAYT.dto.PatientLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientDashboardController {

    @GetMapping("/patient-dashboard")
    public String showPatientHome(Model model) {
        model.addAttribute("hideNavLinks", false);
        return "patient-dashboard";
    }

}
