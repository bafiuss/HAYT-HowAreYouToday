package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.MessageEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.MessageService;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PsychotherapistChat {


    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/psychotherapist-dashboard/patients/patient={id}")
    public String showPsychotherapistChatPage(@PathVariable("id") Long id, Model model, HttpSession session) {
        UserEntity psychotherapist = (PsychotherapistEntity) session.getAttribute("user");
        PatientEntity patientAssociated = userService.getPatientAssociated(id);

        List<MessageEntity> messages = messageService.getChatMessages(psychotherapist.getId(), patientAssociated.getId());

        model.addAttribute("patientId", patientAssociated.getId());
        model.addAttribute("psychotherapistId", psychotherapist.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("patient", patientAssociated);
        model.addAttribute("hideNavLinks",false);

        return "psychotherapist-chat";
    }

}
