package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipSuggestedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipSuggestedRepository extends JpaRepository<TipSuggestedEntity, Long> {
    Optional<TipSuggestedEntity> findTopByPatientOrderByIdDesc(PatientEntity patient);
}

