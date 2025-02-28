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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/selected-psychotherapist")
    public String selectedPsychotherapist(@RequestParam("psychotherapistId") Long psychotherapistId, HttpSession session, Model model){
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        Long patientId = patient.getId();
        userService.psychotherapistAssociation(patientId, psychotherapistId);

        session.setAttribute("hasTherapist", true);
        model.addAttribute("hideNavLinks",false);

        return "patient-dashboard";
    }
}
