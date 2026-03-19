package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PsychotherapistEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PsychotherapistRepository extends JpaRepository<PsychotherapistEntity, Long> {

    PsychotherapistEntity findByEmail(String email);


}
