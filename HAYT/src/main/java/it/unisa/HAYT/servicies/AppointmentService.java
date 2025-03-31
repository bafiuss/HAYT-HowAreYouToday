package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.AppointmentDTO;
import it.unisa.HAYT.entities.AppointmentEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AppointmentEntity> getAppointmentsByPatient(Long psychotherapistId){
        return appointmentRepository.findByPsychotherapistId(psychotherapistId);
    }

    public List<AppointmentDTO> getAllPsychotherapistAppointments(Long psychotherapistId) {
        List<AppointmentEntity> appointments = appointmentRepository.findByPsychotherapistId(psychotherapistId);
        return appointments.stream().map(this::convertPsychotherapistAppointmentsToDTO).collect(Collectors.toList());
    }

    private AppointmentDTO convertPsychotherapistAppointmentsToDTO(AppointmentEntity appointment) {
        AppointmentDTO dto = new AppointmentDTO();

        dto.setTitle(appointment.getTitle());
        dto.setDateTime(appointment.getDateTime());
        dto.setDescription(appointment.getDescription());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPsychotherapistId(appointment.getPsychotherapist().getId());
        dto.setPatientFirstName(appointment.getPatient().getFirstName());
        dto.setPatientLastName(appointment.getPatient().getLastName());

        return dto;
    }

}

