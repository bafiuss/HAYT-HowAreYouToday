package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void saveUser(PatientSignupDTO patientSignupDTO) {
        if (emailExists(patientSignupDTO.getEmail())) {
            return;
        }

        UserEntity user = new UserEntity();

        user.setFirstName(patientSignupDTO.getFirstName());
        user.setLastName(patientSignupDTO.getLastName());
        user.setEmail(patientSignupDTO.getEmail());
        user.setRole("PATIENT");
        user.setPassword(passwordEncoder.encode(patientSignupDTO.getPassword()));

        userRepository.save(user);
    }


}

