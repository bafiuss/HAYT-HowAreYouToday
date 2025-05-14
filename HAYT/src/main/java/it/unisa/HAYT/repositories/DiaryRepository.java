package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
}

