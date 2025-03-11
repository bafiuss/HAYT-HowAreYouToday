package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PatientChatController {

    @Autowired
    private UserService userService;

    @GetMapping("/patient-dashboard/chat")
    public String showPatientChatPage(Model model, HttpSession session){
        UserEntity patient = (PatientEntity) session.getAttribute("user");
        PsychotherapistEntity psychotherapistAssociated = userService.getPsychotherapistAssociated(patient.getId());

        model.addAttribute("psychotherapist", psychotherapistAssociated);
        model.addAttribute("hideNavLinks",false);

        return "patient-chat";
    }

}
