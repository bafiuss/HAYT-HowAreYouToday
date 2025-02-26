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
                    new PsychotherapistEntity("alice.johnson@example.com", "Alice", "Johnson", passwordEncoder.encode("SecurePass1"), "PSYCHOTHERAPIST", "New York"),
                    new PsychotherapistEntity("bob.smith@example.com", "Bob", "Smith", passwordEncoder.encode("StrongPass2"), "PSYCHOTHERAPIST", "Los Angeles"),
                    new PsychotherapistEntity("carol.davis@example.com", "Carol", "Davis", passwordEncoder.encode("Passw0rd3"), "PSYCHOTHERAPIST", "Chicago"),
                    new PsychotherapistEntity("david.white@example.com", "David", "White", passwordEncoder.encode("SafePass4"), "PSYCHOTHERAPIST", "Houston"),
                    new PsychotherapistEntity("emma.brown@example.com", "Emma", "Brown", passwordEncoder.encode("Password5!"), "PSYCHOTHERAPIST", "Miami"),
                    new PsychotherapistEntity("frank.miller@example.com", "Frank", "Miller", passwordEncoder.encode("SecurePass6@"), "PSYCHOTHERAPIST", "San Francisco"),
                    new PsychotherapistEntity("grace.wilson@example.com", "Grace", "Wilson", passwordEncoder.encode("StrongPass7$"), "PSYCHOTHERAPIST", "Boston"),
                    new PsychotherapistEntity("henry.moore@example.com", "Henry", "Moore", passwordEncoder.encode("Passw0rd8#"), "PSYCHOTHERAPIST", "Seattle"),
                    new PsychotherapistEntity("isabella.taylor@example.com", "Isabella", "Taylor", passwordEncoder.encode("SecurePass9%"), "PSYCHOTHERAPIST", "Denver"),
                    new PsychotherapistEntity("jackson.anderson@example.com", "Jackson", "Anderson", passwordEncoder.encode("StrongPass10^"), "PSYCHOTHERAPIST", "Philadelphia"),
                    new PsychotherapistEntity("kate.thomas@example.com", "Kate", "Thomas", passwordEncoder.encode("SafePass11&"), "PSYCHOTHERAPIST", "Washington D.C."),
                    new PsychotherapistEntity("liam.jackson@example.com", "Liam", "Jackson", passwordEncoder.encode("Password12*"), "PSYCHOTHERAPIST", "Atlanta"),
                    new PsychotherapistEntity("mia.harris@example.com", "Mia", "Harris", passwordEncoder.encode("SecurePass13("), "PSYCHOTHERAPIST", "Dallas"),
                    new PsychotherapistEntity("nathan.martin@example.com", "Nathan", "Martin", passwordEncoder.encode("StrongPass14)"), "PSYCHOTHERAPIST", "Phoenix"),
                    new PsychotherapistEntity("olivia.thompson@example.com", "Olivia", "Thompson", passwordEncoder.encode("Passw0rd15_"), "PSYCHOTHERAPIST", "San Diego")
            );

            userRepository.saveAll(psychotherapists);

        }
    }
}
