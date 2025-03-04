package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PsychotherapistRepository extends JpaRepository<PsychotherapistEntity, Long> {

    PsychotherapistEntity findByEmail(String email);


}
