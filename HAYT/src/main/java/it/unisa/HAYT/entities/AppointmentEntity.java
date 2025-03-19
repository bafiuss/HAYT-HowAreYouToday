package it.unisa.HAYT.entities;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime dateTime;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "psychotherapist_id")
    private PsychotherapistEntity psychotherapist;

}
