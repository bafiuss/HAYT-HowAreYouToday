package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.dto.PsychotherapistSignupDTO;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.PsychotherapistRepository;
import it.unisa.HAYT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PsychotherapistRepository psychotherapistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()) )
        );
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<UserEntity> getUser(String email){
        return userRepository.findByEmail(email);
    }

    public void savePatient(PatientSignupDTO patientSignupDTO) {
        if (emailAlreadyExists(patientSignupDTO.getEmail())) {
            return;
        }

        UserEntity user = new UserEntity();

        user.setFirstName(patientSignupDTO.getFirstName());
        user.setLastName(patientSignupDTO.getLastName());
        user.setEmail(patientSignupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(patientSignupDTO.getPassword()));
        user.setRole(UserEntity.Role.PATIENT);

        userRepository.save(user);
    }

    public void savePsychotherapist(PsychotherapistSignupDTO psychotherapistSignupDTO) {
        if (emailAlreadyExists(psychotherapistSignupDTO.getEmail())) {
            return;
        }

        PsychotherapistEntity user = new PsychotherapistEntity();

        user.setFirstName(psychotherapistSignupDTO.getFirstName());
        user.setLastName(psychotherapistSignupDTO.getLastName());
        user.setEmail(psychotherapistSignupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(psychotherapistSignupDTO.getPassword()));
        user.setRole(UserEntity.Role.PSYCHOTHERAPIST);
        user.setGender(psychotherapistSignupDTO.getGender());
        user.setLocation(psychotherapistSignupDTO.getLocation());

        userRepository.save(user);
    }

    public List<PsychotherapistEntity> getAllPsychotherapists() {
        return psychotherapistRepository.findAll();
    }


}

