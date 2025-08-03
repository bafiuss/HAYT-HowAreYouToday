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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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

    public void savePatient(PatientSignupDTO patientSignupDTO, MultipartFile profileImage) {
        if (emailAlreadyExists(patientSignupDTO.getEmail())) {
            return;
        }

        PsychotherapistEntity psychotherapist = psychotherapistRepository.findByEmail("fsessa02@gmail.com");

        PatientEntity patient = new PatientEntity();
        patient.setFirstName(patientSignupDTO.getFirstName());
        patient.setLastName(patientSignupDTO.getLastName());
        patient.setEmail(patientSignupDTO.getEmail());
        patient.setPassword(passwordEncoder.encode(patientSignupDTO.getPassword()));
        patient.setPsychotherapist(psychotherapist);

        try {
            patient.setProfileImage(profileImage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        userRepository.save(patient);
    }

    public void savePsychotherapist(PsychotherapistSignupDTO psychotherapistSignupDTO, MultipartFile profileImage) {
        if (emailAlreadyExists(psychotherapistSignupDTO.getEmail())) {
            return;
        }

        PsychotherapistEntity psychotherapist = new PsychotherapistEntity();

        psychotherapist.setFirstName(psychotherapistSignupDTO.getFirstName());
        psychotherapist.setLastName(psychotherapistSignupDTO.getLastName());
        psychotherapist.setEmail(psychotherapistSignupDTO.getEmail());
        psychotherapist.setPassword(passwordEncoder.encode(psychotherapistSignupDTO.getPassword()));
        psychotherapist.setAlboRegion(psychotherapistSignupDTO.getAlboRegion());

        try {
            psychotherapist.setProfileImage(profileImage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        userRepository.save(psychotherapist);
    }

    public int getNumberOfPatientsAssociated(Long psychotherapistId){
        return patientRepository.numberOfPatientsAssociated(psychotherapistId);
    }

    public List<PatientEntity> getFirstTwoUsers(Long psychotherapistId){
        return patientRepository.findFirstTwoPatientsAssociated(psychotherapistId, PageRequest.of(0, 2));
    }

    public List<PatientEntity> getPatientsAssociated(Long psychotherapistId){
        return patientRepository.patientList(psychotherapistId);
    }

    public PsychotherapistEntity getPsychotherapistAssociated(Long patientId){
        return patientRepository.findPsychotherapistAssociated(patientId);
    }

    public PatientEntity getPatientAssociated(Long patientId){
        return patientRepository.findPatientAssociated(patientId);
    }
}