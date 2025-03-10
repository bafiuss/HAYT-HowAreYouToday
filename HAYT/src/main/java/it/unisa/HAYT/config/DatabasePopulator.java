package it.unisa.HAYT.config;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.PsychotherapistRepository;
import it.unisa.HAYT.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
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
    public void populate() throws IOException {
        if (userRepository.count() == 0) {

            byte[] psychotherapistImage = loadImage("psychotherapist.jpg");

            byte[] imagePatient1 = loadImage("patient-1.jpg");
            byte[] imagePatient2 = loadImage("patient-2.jpg");
            byte[] imagePatient3 = loadImage("patient-3.jpg");

            PsychotherapistEntity psychotherapist = new PsychotherapistEntity("fsessa02@gmail.com", "Fabio", "Sessa", passwordEncoder.encode("Prova123"), "Campania - 150", psychotherapistImage);

            List<UserEntity> patients = List.of(
                    new PatientEntity("lucaverdi@gmail.com", "Luca", "Verdi", passwordEncoder.encode("Passw0rd3"), psychotherapist, imagePatient1),
                    new PatientEntity("mariorossi@gmail.com", "Mario", "Rossi", passwordEncoder.encode("SecurePass1"), psychotherapist,imagePatient2),
                    new PatientEntity("giorgiagallo@gmail.com", "Giorgia", "Gallo", passwordEncoder.encode("SafePass5"), psychotherapist, imagePatient3)
            );

            userRepository.save(psychotherapist);
            userRepository.saveAll(patients);
        }
    }

    private byte[] loadImage(String imagePath) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/images/profile-images/" + imagePath);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }


}
