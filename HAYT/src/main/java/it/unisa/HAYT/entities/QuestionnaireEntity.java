package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questionnaires")
public class QuestionnaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;


    private int depressedMood;
    private int lossOfInterest;
    private int appetiteChanges;
    private int insomnia;
    private int psychomotorAgitation;
    private int fatigue;
    private int worthlessness;
    private int difficultyConcentratingDepression;

    private int excessiveWorry;
    private int difficultyControllingWorry;
    private int restlessness;
    private int easyFatigue;
    private int difficultyConcentratingAnxiety;
    private int irritability;
    private int muscleTension;
    private int sleepDisturbances;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
