package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    @Query("SELECT Count(*) FROM DiaryEntity d WHERE d.patient.psychotherapist.id = :psychotherapistId")
    int numberOfDiaryEntriesAssociated(@Param("psychotherapistId") Long psychotherapistId);
}

