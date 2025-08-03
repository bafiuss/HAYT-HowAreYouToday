package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByEmail(String email);

    @Query("SELECT Count(*) FROM PatientEntity p WHERE p.psychotherapist.id = :psychotherapistId")
    int numberOfPatientsAssociated(@Param("psychotherapistId") Long psychotherapistId);

    @Query("SELECT p FROM PatientEntity p WHERE p.psychotherapist.id = :psychotherapistId")
    List<PatientEntity> findFirstTwoPatientsAssociated(@Param("psychotherapistId") Long psychotherapistId, Pageable pageable);

    @Query("SELECT p FROM PatientEntity p WHERE p.psychotherapist.id = :psychotherapistId")
    List<PatientEntity> patientList(@Param("psychotherapistId") Long psychotherapistId);

    @Query("SELECT p.psychotherapist FROM PatientEntity p WHERE p.id = :patientId")
    PsychotherapistEntity findPsychotherapistAssociated(@Param("patientId") Long patientId);

    @Query("SELECT p FROM PatientEntity p WHERE p.id = :patientId")
    PatientEntity findPatientAssociated(@Param("patientId") Long patientId);


}