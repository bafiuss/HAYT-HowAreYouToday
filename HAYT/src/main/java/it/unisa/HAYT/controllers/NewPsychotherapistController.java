package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewPsychotherapistController {

    @Autowired
    private UserService userService;

    @GetMapping("/patient-dashboard/new-psychotherapist")
    public String showNewPsychotherapistPage(Model model){
        List<PsychotherapistEntity> psychotherapistEntityList = userService.getAllPsychotherapists();
        model.addAttribute("psychotherapists", psychotherapistEntityList);
        model.addAttribute("hideNavLinks",false);
        return "new-psychotherapist";
    }
}
