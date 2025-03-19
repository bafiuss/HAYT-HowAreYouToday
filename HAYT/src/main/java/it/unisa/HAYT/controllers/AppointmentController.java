package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.servicies.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/save-appointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentDTO request) {
        String response = appointmentService.saveAppointment(request);
        System.out.println("ID paziente: " + request.getPatientId());
        System.out.println("ID terapeuta: " + request.getPsychotherapistId());
        if (response.equals("Appointment created successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

