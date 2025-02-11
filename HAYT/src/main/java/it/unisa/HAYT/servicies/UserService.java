package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    public boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean userExists(String email, String password){
        return userRepository.findByEmailAndPassword(email,password).isPresent();
    }

    public String getUserRole(String email) {
        return userRepository.findRoleByEmail(email).orElse("null");
    }

    public UserEntity getUser(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public void savePatient(PatientSignupDTO patientSignupDTO) {
        if (emailAlreadyExists(patientSignupDTO.getEmail())) {
            return;
        }

        UserEntity user = new UserEntity();

        user.setFirstName(patientSignupDTO.getFirstName());
        user.setLastName(patientSignupDTO.getLastName());
        user.setEmail(patientSignupDTO.getEmail());
        user.setPassword(patientSignupDTO.getPassword());
        user.setRole("PATIENT");

        userRepository.save(user);
    }


}

