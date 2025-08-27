package it.unisa.HAYT.entities;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipEntity;
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
@Table(name = "tip_selection")
public class TipSelectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "tip_id", nullable = false)
    private TipEntity tip;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters e setters
}

