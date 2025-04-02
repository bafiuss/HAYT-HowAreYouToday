package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.entities.AppointmentEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.servicies.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PsychotherapistAppointmentsController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/psychotherapist-appointments")
    public List<AppointmentDTO> getPsychotherapistAppointments(HttpSession session) {
        PsychotherapistEntity psychotherapist = (PsychotherapistEntity) session.getAttribute("user");

        return appointmentService.getAllPsychotherapistAppointments(psychotherapist.getId());
    }

    @GetMapping("/psychotherapist-appointments/{patientId}")
    public List<AppointmentEntity> getAppointmentsWithPatient(@PathVariable Long patientId, HttpSession session) {
        PsychotherapistEntity psychotherapist = (PsychotherapistEntity) session.getAttribute("user");

        return appointmentService.getAppointmentsByPatientAndTherapist(patientId, psychotherapist.getId());
    }


}
