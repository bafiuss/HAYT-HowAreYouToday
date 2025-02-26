package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "psychotherapists")
@PrimaryKeyJoinColumn(name = "user_id")
public class PsychotherapistEntity extends UserEntity{

    private String location;
    private String gender;

    public PsychotherapistEntity(String email, String firstName, String lastName, String password, Role role, String gender, String location) {
        super(email, firstName, lastName, password, role);
        this.gender = gender;
        this.location = location;
    }
}