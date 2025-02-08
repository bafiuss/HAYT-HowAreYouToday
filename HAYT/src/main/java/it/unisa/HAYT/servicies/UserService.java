package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean userExists(String email, String password){
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                return true;
            }
        }
        return false;
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
        //user.setPassword(patientSignupDTO.getPassword());
        user.setPassword(passwordEncoder.encode(patientSignupDTO.getPassword()));

        userRepository.save(user);
    }


}

