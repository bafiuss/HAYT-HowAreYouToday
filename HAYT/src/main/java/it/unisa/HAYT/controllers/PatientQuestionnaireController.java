package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.QuestionnaireDTO;
import it.unisa.HAYT.servicies.QuestionnaireService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PatientQuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping("/save-questionnaire")
    public String submitQuestionnaire(@Valid @ModelAttribute("questionnaireDTO") QuestionnaireDTO questionnaireDTO,
                                      Model model
    ){
        questionnaireService.saveQuestionnaire(questionnaireDTO);

        model.addAttribute("hideNavLinks", false);
        model.addAttribute("questionnaireDTO", new QuestionnaireDTO());

        return "patient-dashboard";
    }


}
