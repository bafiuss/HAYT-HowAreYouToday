package it.unisa.HAYT.dto;

import it.unisa.HAYT.entities.AppointmentEntity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {

    private String title;
    private LocalDateTime dateTime;
    private String description;
    private Long patientId;
    private Long psychotherapistId;

    private String patientFirstName;
    private String patientLastName;
}

