package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.QuestionnaireDTO;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.QuestionnaireEntity;
import it.unisa.HAYT.repositories.QuestionnaireRepository;
import it.unisa.HAYT.servicies.QuestionnaireService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PatientQuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping("/save-questionnaire")
    public String submitQuestionnaire(@Valid @ModelAttribute("questionnaireDTO") QuestionnaireDTO questionnaireDTO,
                                      Model model,
                                      HttpSession session)
    {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        questionnaireService.saveQuestionnaire(questionnaireDTO, patient);
        long secondsLeft = questionnaireService.getSecondsUntilNextQuestionnaire(patient);

        model.addAttribute("countdown", secondsLeft);
        model.addAttribute("hideNavLinks", false);
        model.addAttribute("questionnaireDTO", new QuestionnaireDTO());

        return "patient-dashboard";
    }


}
