package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.dto.PsychotherapistSignupDTO;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.PsychotherapistEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.PatientRepository;
import it.unisa.HAYT.repositories.PsychotherapistRepository;
import it.unisa.HAYT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PsychotherapistRepository psychotherapistRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );


    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void savePatient(PatientSignupDTO patientSignupDTO) {
        if (emailAlreadyExists(patientSignupDTO.getEmail())) {
            return;
        }

        PatientEntity patient = new PatientEntity();
        PsychotherapistEntity psychotherapist = psychotherapistRepository.findByEmail("fsessa02@gmail.com");


        patient.setFirstName(patientSignupDTO.getFirstName());
        patient.setLastName(patientSignupDTO.getLastName());
        patient.setEmail(patientSignupDTO.getEmail());
        patient.setPassword(passwordEncoder.encode(patientSignupDTO.getPassword()));
        patient.setPsychotherapist(psychotherapist);

        userRepository.save(patient);
    }

    public void savePsychotherapist(PsychotherapistSignupDTO psychotherapistSignupDTO) {
        if (emailAlreadyExists(psychotherapistSignupDTO.getEmail())) {
            return;
        }

        PsychotherapistEntity psychotherapist = new PsychotherapistEntity();

        psychotherapist.setFirstName(psychotherapistSignupDTO.getFirstName());
        psychotherapist.setLastName(psychotherapistSignupDTO.getLastName());
        psychotherapist.setEmail(psychotherapistSignupDTO.getEmail());
        psychotherapist.setPassword(passwordEncoder.encode(psychotherapistSignupDTO.getPassword()));
        psychotherapist.setAlboRegion(psychotherapistSignupDTO.getAlboRegion());

        userRepository.save(psychotherapist);
    }

    public int getNumberOfPatientsAssociated(Long psychotherapistId){
        return patientRepository.numberOfPatientsAssociated(psychotherapistId);
    }

}

