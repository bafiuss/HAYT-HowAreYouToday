package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}
