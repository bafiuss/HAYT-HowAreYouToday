package it.unisa.HAYT.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "patients")
public class PatientEntity extends UserEntity{

    @ManyToOne
    @JoinColumn(name = "psychotherapist_id")
    private PsychotherapistEntity psychotherapist;

    public PatientEntity(String firstName, String lastName, String email, String password, PsychotherapistEntity psychotherapist) {
        super(firstName, lastName, email, password);
        this.psychotherapist = psychotherapist;
    }

    @Override
    public String getRole() {
        return "PATIENT";
    }


}
