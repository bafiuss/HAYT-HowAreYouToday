package it.unisa.HAYT.entities;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime dateTime;

    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "psychotherapist_id")
    private PsychotherapistEntity psychotherapist;

    public AppointmentEntity(String title, LocalDateTime dateTime, String description, PatientEntity patient, PsychotherapistEntity psychotherapist) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.patient = patient;
        this.psychotherapist = psychotherapist;
    }
}
