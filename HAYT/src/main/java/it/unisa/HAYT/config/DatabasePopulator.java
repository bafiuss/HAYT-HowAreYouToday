package it.unisa.HAYT.config;

import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.PsychotherapistRepository;
import it.unisa.HAYT.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabasePopulator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PsychotherapistRepository psychotherapistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void populate() {
        if (userRepository.count() == 0) {
            List<UserEntity> psychotherapists = List.of(
                    new PsychotherapistEntity("mariorossi@gmail.com", "Mario", "Rossi", passwordEncoder.encode("SecurePass1"), UserEntity.Role.PSYCHOTHERAPIST, "Male", "Roma"),
                    new PsychotherapistEntity("lucaverdi@gmail.com", "Luca", "Verdi", passwordEncoder.encode("Passw0rd3"), UserEntity.Role.PSYCHOTHERAPIST, "Male", "Napoli"),
                    new PsychotherapistEntity("marcoconti@gmail.com", "Marco", "Conti", passwordEncoder.encode("StrongPass7"), UserEntity.Role.PSYCHOTHERAPIST, "Male", "Bologna"),
                    new PsychotherapistEntity("annabianchi@gmail.com", "Anna", "Bianchi", passwordEncoder.encode("StrongPass"), UserEntity.Role.PSYCHOTHERAPIST, "Female", "Milano"),
                    new PsychotherapistEntity("giorgiagallo@gmail.com", "Giorgia", "Gallo", passwordEncoder.encode("SafePass"), UserEntity.Role.PSYCHOTHERAPIST, "Female", "Torino"),
                    new PsychotherapistEntity("francescotesta@gmail.com", "Francesco", "Testa", passwordEncoder.encode("Password5"), UserEntity.Role.PSYCHOTHERAPIST, "Male", "Palermo"),
                    new PsychotherapistEntity("elisaferretti@gmail.com", "Elisa", "Ferretti", passwordEncoder.encode("SecurePass6"), UserEntity.Role.PSYCHOTHERAPIST, "Female", "Firenze"),
                    new PsychotherapistEntity("saramoretti@gmail.com", "Sara", "Moretti", passwordEncoder.encode("Passw0rd8"), UserEntity.Role.PSYCHOTHERAPIST, "Female", "Genova"),
                    new PsychotherapistEntity("valentinabarone@gmail.com", "Valentina", "Barone", passwordEncoder.encode("SecurePass9"), UserEntity.Role.PSYCHOTHERAPIST, "Female", "Venezia")
            );

            userRepository.saveAll(psychotherapists);

        }
    }
}
