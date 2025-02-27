package it.unisa.HAYT.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class PatientEntity extends UserEntity{

    @ManyToOne
    @JoinColumn(name = "psychotherapist_id")
    private PsychotherapistEntity psychotherapist;
    @Override
    public String getRole() {
        return "PATIENT";
    }


}
