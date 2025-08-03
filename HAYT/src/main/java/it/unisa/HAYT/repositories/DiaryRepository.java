package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}

