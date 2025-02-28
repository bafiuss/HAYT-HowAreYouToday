package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE PatientEntity p SET p.psychotherapist.id = :psychotherapistId WHERE p.id = :patientId")
    int updatePsychotherapist(@Param("patientId") Long patientId, @Param("psychotherapistId") Long psychotherapistId);

    @Query("SELECT p.psychotherapist FROM PatientEntity p WHERE p.id = :patientId")
    Optional<PsychotherapistEntity> getAssociatedPsychotherapist(@Param("patientId") Long patientId);

    @Query("SELECT p FROM PsychotherapistEntity p WHERE p.id <> (SELECT p.psychotherapist.id FROM PatientEntity p WHERE p.id = :patientId)")
    List<PsychotherapistEntity> findPsychotherapistsExcludingPatient(@Param("patientId") Long patientId);

}