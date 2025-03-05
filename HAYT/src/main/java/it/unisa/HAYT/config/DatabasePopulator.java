package it.unisa.HAYT.config;

import it.unisa.HAYT.entities.PatientEntity;
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

            PsychotherapistEntity psychotherapist = new PsychotherapistEntity("fsessa02@gmail.com","Fabio","Sessa", passwordEncoder.encode("Prova123"),"Campania - 150");

            List<UserEntity> patients = List.of(
                    new PatientEntity("mariorossi@gmail.com", "Mario", "Rossi", passwordEncoder.encode("SecurePass1"), psychotherapist),
                    new PatientEntity("lucaverdi@gmail.com", "Luca", "Verdi", passwordEncoder.encode("Passw0rd3"), psychotherapist),
                    new PatientEntity("marcoconti@gmail.com", "Marco", "Conti", passwordEncoder.encode("StrongPass7"), psychotherapist),
                    new PatientEntity("annabianchi@gmail.com", "Anna", "Bianchi", passwordEncoder.encode("StrongPass2"), psychotherapist),
                    new PatientEntity("giorgiagallo@gmail.com", "Giorgia", "Gallo", passwordEncoder.encode("SafePass5"), psychotherapist),
                    new PatientEntity("francescotesta@gmail.com", "Francesco", "Testa", passwordEncoder.encode("Password5"), psychotherapist),
                    new PatientEntity("elisaferretti@gmail.com", "Elisa", "Ferretti", passwordEncoder.encode("SecurePass6"), psychotherapist),
                    new PatientEntity("saramoretti@gmail.com", "Sara", "Moretti", passwordEncoder.encode("Passw0rd8"), psychotherapist),
                    new PatientEntity("valentinabarone@gmail.com", "Valentina", "Barone", passwordEncoder.encode("SecurePass9"), psychotherapist)
            );

            userRepository.save(psychotherapist);
            userRepository.saveAll(patients);

        }
    }
}
