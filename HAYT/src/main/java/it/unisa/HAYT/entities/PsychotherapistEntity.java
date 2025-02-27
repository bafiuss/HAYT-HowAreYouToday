package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "psychotherapists")
public class PsychotherapistEntity extends UserEntity{

    private String alboRegion;
    private String gender;

    @OneToMany(mappedBy = "psychotherapist")
    private List<PatientEntity> patients;

    public PsychotherapistEntity(String email, String firstName, String lastName, String password, String gender, String alboRegion) {
        super(email, firstName, lastName, password);
        this.gender = gender;
        this.alboRegion = alboRegion;
    }

    @Override
    public String getRole() {
        return "PSYCHOTHERAPIST";
    }
}