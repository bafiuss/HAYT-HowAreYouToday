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

    public enum Mood {joy, trust, fear, surprise, sadness, disgust, anger, anticipation,
                    love, submission, awe, disapproval, remorse, contempt, aggressiveness, optimism,
    }
    public enum Sentiment{negative, neutral, positive}

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    private LocalDateTime createdAt = LocalDateTime.now();

    public DiaryEntity(String content, LocalDateTime createdAt, Mood mood, Sentiment sentiment, String title, PatientEntity patient){
        this.content = content;
        this.createdAt = createdAt;
        this.mood = mood;
        this.sentiment = sentiment;
        this.title = title;
        this.patient = patient;
    }

}