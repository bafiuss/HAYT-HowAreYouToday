package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    @Query("SELECT u.role FROM UserEntity u WHERE u.email = :email")
    Optional<String> findRoleByEmail(@Param("email") String email);
}

