package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.entities.AppointmentEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public String saveAppointment(AppointmentDTO request) {
        PatientEntity patient = userService.getPatientAssociated(request.getPatientId());
        PsychotherapistEntity psychotherapist = userService.getPsychotherapistAssociated(request.getPatientId());

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setTitle(request.getTitle());
        appointment.setDateTime(request.getDateTime());
        appointment.setDescription(request.getDescription());
        appointment.setPatient(patient);
        appointment.setPsychotherapist(psychotherapist);

        appointmentRepository.save(appointment);

        return "Appointment created successfully";
    }
}

