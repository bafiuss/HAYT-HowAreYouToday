package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipEntity;
import it.unisa.HAYT.entities.TipFavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipFavoriteRepository extends JpaRepository<TipFavoriteEntity, Long> {
    Optional<TipFavoriteEntity> findByPatientAndTip(PatientEntity patient, TipEntity tip);

    void deleteByPatientAndTip(PatientEntity patient, TipEntity tip);

    List<TipFavoriteEntity> findByPatient(PatientEntity patient);
}

