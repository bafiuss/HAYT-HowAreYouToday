package it.unisa.HAYT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddPsychotherapistController {

    @GetMapping("/patient-dashboard/new-psychotherapist")
    public String showNewPsychotherapistPage(Model model){
        model.addAttribute("hideNavLinks",false);
        return "new-psychotherapist";
    }
}
