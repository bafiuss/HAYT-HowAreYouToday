package it.unisa.HAYT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientDashboardController {

    @GetMapping("/patient-dashboard")
    public String showPatientDashboardPage(Model model) {
        model.addAttribute("hideNavLinks", false);
        model.addAttribute("currentPage", "patient-dashboard");

        return "patient-dashboard";
    }


}
