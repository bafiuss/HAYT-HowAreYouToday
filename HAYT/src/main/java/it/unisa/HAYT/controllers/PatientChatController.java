package it.unisa.HAYT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientChatController {

    @GetMapping("/patient-dashboard/chat")
    public String showPatientChatPage(Model model){
        model.addAttribute("hideNavLinks",false);
        return "patient-chat";
    }

}
