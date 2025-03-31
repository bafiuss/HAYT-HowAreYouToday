package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.servicies.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PsychotherapistAppointmentsController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/psychotherapist-appointments")
    public List<AppointmentDTO> getPsychotherapistAppointments() {
        return appointmentService.getAllAppointments();
    }
}
