package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipCompletedEntity;
import it.unisa.HAYT.entities.TipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipCompletedRepository extends JpaRepository<TipCompletedEntity, Long> {

    List<TipCompletedEntity> findByPatient(PatientEntity patient);
    boolean existsByPatientAndTip(PatientEntity patient, TipEntity tip);
}

