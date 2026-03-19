package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
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

    private int impactWorkStudy;
    private int impactSocial;
    private int impactLeisure;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public QuestionnaireEntity(PatientEntity patient, int depressedMood, int lossOfInterest, int appetiteChanges, int insomnia, int psychomotorAgitation, int fatigue, int worthlessness, int difficultyConcentratingDepression, int excessiveWorry, int difficultyControllingWorry, int restlessness, int easyFatigue, int difficultyConcentratingAnxiety, int irritability, int muscleTension, int sleepDisturbances, int impactWorkStudy, int impactSocial, int impactLeisure, LocalDateTime completedAt) {
        this.patient = patient;
        this.depressedMood = depressedMood;
        this.lossOfInterest = lossOfInterest;
        this.appetiteChanges = appetiteChanges;
        this.insomnia = insomnia;
        this.psychomotorAgitation = psychomotorAgitation;
        this.fatigue = fatigue;
        this.worthlessness = worthlessness;
        this.difficultyConcentratingDepression = difficultyConcentratingDepression;
        this.excessiveWorry = excessiveWorry;
        this.difficultyControllingWorry = difficultyControllingWorry;
        this.restlessness = restlessness;
        this.easyFatigue = easyFatigue;
        this.difficultyConcentratingAnxiety = difficultyConcentratingAnxiety;
        this.irritability = irritability;
        this.muscleTension = muscleTension;
        this.sleepDisturbances = sleepDisturbances;
        this.completedAt = completedAt;
        this.impactWorkStudy = impactWorkStudy;
        this.impactSocial = impactSocial;
        this.impactLeisure = impactLeisure;
    }
}
