package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    private String mood;

    private String sentiment;

    private LocalDateTime createdAt = LocalDateTime.now();

}