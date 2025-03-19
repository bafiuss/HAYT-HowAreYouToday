package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.servicies.AppointmentService;
import it.unisa.HAYT.servicies.MessageService;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class PatientChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patient-dashboard/chat")
    public String showPatientChatPage(Model model, HttpSession session){
        UserEntity patient = (PatientEntity) session.getAttribute("user");
        PsychotherapistEntity psychotherapistAssociated = userService.getPsychotherapistAssociated(patient.getId());

        List<MessageEntity> messages = messageService.getChatMessages(patient.getId(), psychotherapistAssociated.getId());

        model.addAttribute("patientId", patient.getId());
        model.addAttribute("psychotherapistId", psychotherapistAssociated.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("psychotherapist", psychotherapistAssociated);
        model.addAttribute("hideNavLinks",false);

        return "patient-chat";
    }

    @PostMapping("/save-appointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentDTO request, Model model) {
        String response = appointmentService.saveAppointment(request);

        if (response.equals("Appointment created successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageEntity sendMessage(@Payload ChatMessage chatMessage) {
        return messageService.saveMessage(chatMessage.getSenderId(), chatMessage.getReceiverId(), chatMessage.getContent());
    }



}
