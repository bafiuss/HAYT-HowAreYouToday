package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.DiaryEntity;
import it.unisa.HAYT.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    @Query("SELECT Count(*) FROM DiaryEntity d WHERE d.patient.psychotherapist.id = :psychotherapistId")
    int numberOfDiaryEntriesAssociated(@Param("psychotherapistId") Long psychotherapistId);

    Optional<DiaryEntity> findTopByPatientOrderByCreatedAtDesc(PatientEntity patient);
}

