package it.unisa.HAYT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PsychotherapistDashboardController {

    @GetMapping("/psychotherapist-dashboard")
    public String showPsychotherapistDashboardPage(Model model) {
        model.addAttribute("hideNavLinks", false);

        return "psychotherapist-dashboard";
    }


}
