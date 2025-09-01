package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.entities.TipEntity.Type;
import it.unisa.HAYT.servicies.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PsychotherapistChat {


    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private TipService tipService;

    @GetMapping("/psychotherapist-dashboard/patients/patient={id}")
    public String showPsychotherapistChatPage(@PathVariable("id") Long id, Model model, HttpSession session) {
        UserEntity psychotherapist = (PsychotherapistEntity) session.getAttribute("user");
        PatientEntity patientAssociated = userService.getPatientAssociated(id);

        List<MessageEntity> messages = messageService.getChatMessages(psychotherapist.getId(), patientAssociated.getId());
        List<QuestionnaireEntity> questionnaires = questionnaireService.getPatientQuestionnaires(id);
        List<DiaryEntity> diaryEntries = diaryService.findByPatientIdOrderByCreatedAtDesc(id);

        model.addAttribute("patientId", patientAssociated.getId());
        model.addAttribute("psychotherapistId", psychotherapist.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("questionnaires",questionnaires);
        model.addAttribute("diaryEntries", diaryEntries);
        model.addAttribute("patient", patientAssociated);
        model.addAttribute("hideNavLinks",false);

        model.addAttribute("breathingTips", tipService.getTipsByType(Type.valueOf("breathingExercise")));
        model.addAttribute("muscleTips", tipService.getTipsByType(Type.valueOf("muscleRelaxation")));
        model.addAttribute("mindfulnessTips", tipService.getTipsByType(Type.valueOf("mindfulnessMeditation")));

        model.addAttribute("completedCounts", tipService.getCompletedCounts(patientAssociated.getId()));
        model.addAttribute("suggestedCounts", tipService.getSuggestedCounts(patientAssociated.getId()));

        return "psychotherapist-chat";
    }


    @GetMapping("/api/questionnaires/{questionnaireId}")
    @ResponseBody
    public ResponseEntity<QuestionnaireEntity> getQuestionnaire(@PathVariable Long questionnaireId) {
        QuestionnaireEntity questionnaire = questionnaireService.getQuestionnaireByIdWithDetails(questionnaireId);

        return ResponseEntity.ok(questionnaire);
    }

}
