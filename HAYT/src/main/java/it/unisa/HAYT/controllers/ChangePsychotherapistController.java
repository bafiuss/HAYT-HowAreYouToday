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

import java.util.List;
import java.util.Optional;

@Controller
public class ChangePsychotherapistController {

    @Autowired
    private UserService userService;

    @GetMapping("/patient-dashboard/change-psychotherapist")
    public String showChangePsychotherapistPage(Model model, HttpSession session){
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        Long patientId = patient.getId();

        Optional<PsychotherapistEntity> associatedPsychotherapist = userService.getAssociatedPsychotherapist(patientId);
        List<PsychotherapistEntity> psychotherapistsExcludingPatient = userService.findPsychotherapistsExcludingPatient(patientId);

        model.addAttribute("psychotherapists", psychotherapistsExcludingPatient);
        model.addAttribute("associatedPsychotherapist", associatedPsychotherapist);
        model.addAttribute("hideNavLinks",false);

        return "change-psychotherapist";
    }

}
