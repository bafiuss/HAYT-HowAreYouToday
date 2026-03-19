package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.TipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import it.unisa.HAYT.entities.TipEntity.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipRepository extends JpaRepository<TipEntity, Long> {

    List<TipEntity> findByType(Type type);

}
