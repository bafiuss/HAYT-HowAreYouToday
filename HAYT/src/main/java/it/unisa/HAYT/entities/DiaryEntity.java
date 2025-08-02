package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diary_thoughts")
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public enum Mood {admiration, amusement, anger, annoyance, approval, caring, confusion, curiosity, desire, disappointment,
        disapproval, disgust, embarrassment, excitement, fear, gratitude, grief, joy, love, nervousness, optimism, pride, realization, relief, remorse, sadness, surprise, neutral
    }


    private Mood mood;
    private String sentiment;
    private LocalDateTime createdAt = LocalDateTime.now();

    public DiaryEntity(String content, LocalDateTime createdAt, Mood mood, String sentiment, String title, PatientEntity patient){
        this.content = content;
        this.createdAt = createdAt;
        this.mood = mood;
        this.sentiment = sentiment;
        this.title = title;
        this.patient = patient;
    }

}