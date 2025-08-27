package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipSelectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipSelectionRepository extends JpaRepository<TipSelectionEntity, Long> {
    List<TipSelectionEntity> findByPatient(PatientEntity patient);
    void deleteByPatient(PatientEntity patient);
}
