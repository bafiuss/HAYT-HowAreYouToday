package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.QuestionnaireDTO;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.servicies.QuestionnaireService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientDashboardController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/patient-dashboard")
    public String showPatientDashboardPage(Model model, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        long secondsLeft = questionnaireService.getSecondsUntilNextQuestionnaire(patient);

        model.addAttribute("hideNavLinks", false);
        model.addAttribute("currentPage", "patient-dashboard");
        model.addAttribute("questionnaireDTO", new QuestionnaireDTO());
        model.addAttribute("countdown", secondsLeft);

        return "patient-dashboard";
    }


}
