package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientSignupDto;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/authentication")
    public String authenticationPage(HttpServletRequest request, Model model) {
        PatientSignupDto patientSignupDto = new PatientSignupDto();
        model.addAttribute("currentUrl", request.getRequestURI());
        model.addAttribute(patientSignupDto);
        model.addAttribute("success", false);
        return "authentication";
    }

    @PostMapping("/patient-signup")
    public String signUp(Model model, @Valid @ModelAttribute PatientSignupDto patientSignupDto, BindingResult result){

        if(!patientSignupDto.getPassword().equals(patientSignupDto.getConfirmPassword())){
            result.addError(
                    new FieldError("patientSignupDto", "confirmPassword", "Password and Confirm password do not match")
            );
        }

        UserEntity userEntity = userRepository.findByEmail(patientSignupDto.getEmail());
        if(userEntity != null){
            result.addError(
                    new FieldError("patientSignupDto", "email", "Email address is already used")
            );
        }

        if(result.hasErrors()) return "authentication";

        try{

            var bCryptEncoder = new BCryptPasswordEncoder();

            UserEntity user = new UserEntity();
            user.setFirstName(patientSignupDto.getFirstName());
            user.setLastName(patientSignupDto.getLastName());
            user.setEmail(patientSignupDto.getEmail());
            user.setPassword(bCryptEncoder.encode(patientSignupDto.getPassword()));
            user.setRole("patient");

            userRepository.save(user);

            model.addAttribute("patientSignupDto", new PatientSignupDto() );
            model.addAttribute("success", true);

        }catch (Exception ex){
            result.addError(
                    new FieldError("patientSignupDto", "firstName", ex.getMessage())
            );
        }

        return "authentication";
    }

    @GetMapping("/psychologist-signup")
    public String psychologistSignupPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUrl", request.getRequestURI());
        return "psychologist-signup";
    }




}
