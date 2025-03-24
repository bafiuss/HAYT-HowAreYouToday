package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.entities.AppointmentEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public void saveAppointment(AppointmentDTO request) {
        PatientEntity patient = userService.getPatientAssociated(request.getPatientId());
        PsychotherapistEntity psychotherapist = userService.getPsychotherapistAssociated(request.getPatientId());

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setTitle(request.getTitle());
        appointment.setDateTime(request.getDateTime());
        appointment.setDescription(request.getDescription());
        appointment.setPatient(patient);
        appointment.setPsychotherapist(psychotherapist);

        appointmentRepository.save(appointment);

    }

    public List<AppointmentEntity> getAppointmentsByPatientAndTherapist(Long patientId, Long psychotherapistId) {
        return appointmentRepository.findByPatientIdAndPsychotherapistId(patientId, psychotherapistId);
    }

}

