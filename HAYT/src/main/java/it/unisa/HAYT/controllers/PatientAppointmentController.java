package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.entities.AppointmentEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.servicies.AppointmentService;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping ("/patient-appointments")
    public List<AppointmentEntity> getAllPatientAppointments(HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        PsychotherapistEntity psychotherapistAssociated = userService.getPsychotherapistAssociated(patient.getId());

        return appointmentService.getAppointmentsByPatientAndTherapist(patient.getId(), psychotherapistAssociated.getId());
    }

    @GetMapping ("/psychotherapist-associated-appointments")
    public List<AppointmentEntity> getAllPsychotherapistAppointments(HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        PsychotherapistEntity psychotherapistAssociated = userService.getPsychotherapistAssociated(patient.getId());

        return appointmentService.getAppointmentsByPatient(psychotherapistAssociated.getId());
    }

    @PostMapping("/save-appointment")
    public String createAppointment(@RequestBody AppointmentDTO request) {
        appointmentService.saveAppointment(request);

        return "patient-chat";
    }
}

