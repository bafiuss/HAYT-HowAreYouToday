package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.PatientSignupDTO;
import it.unisa.HAYT.dto.PsychotherapistSignupDTO;
import it.unisa.HAYT.servicies.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class SignupController {

    @Autowired
    private UserService userService;


    @GetMapping("/signup")
    public String showPatientSignupPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            model.addAttribute("hideNavLinks", true);
            model.addAttribute("patientSignupDTO", new PatientSignupDTO());
            model.addAttribute("successSignup", false);
            model.addAttribute("failedSignup", false);

            return "signup";
        }

        model.addAttribute("hideNavLinks", false);

        return "index";
    }

    @PostMapping("/patient-signup")
    public String patientSignup(@Valid @ModelAttribute("patientSignupDTO") PatientSignupDTO patientSignupDTO,
                                @RequestParam("profileImage") MultipartFile profileImage,
                                Model model)
    {

        if(userService.emailAlreadyExists(patientSignupDTO.getEmail())) {
            model.addAttribute("failedSignup", true);
            return "signup";
        }

        userService.savePatient(patientSignupDTO,profileImage);

        model.addAttribute("hideNavLinks", true);
        model.addAttribute("patientSignupDTO", new PatientSignupDTO());
        model.addAttribute("successSignup", true);

        return "signup";
    }

    @GetMapping("/psychotherapist-signup")
    public String showPsychotherapistSignupPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
              model.addAttribute("hideNavLinks", true);
              model.addAttribute("psychotherapistSignupDTO", new PsychotherapistSignupDTO());
              model.addAttribute("successSignup", false);
              model.addAttribute("failedSignup", false);

              return "psychotherapist-signup";
        }

        model.addAttribute("hideNavLinks", false);

        return "psychotherapist-signup";
    }

    @PostMapping("/psychotherapist-signup")
    public String psychotherapistSignup(@Valid @ModelAttribute("psychotherapistSignupDTO") PsychotherapistSignupDTO psychotherapistSignupDTO,
                                        @RequestParam("profileImage") MultipartFile profileImage,
                                        Model model)
    {

        if(userService.emailAlreadyExists(psychotherapistSignupDTO.getEmail())) {
            model.addAttribute("failedSignup", true);
            return "psychotherapist-signup";
        }

        userService.savePsychotherapist(psychotherapistSignupDTO,profileImage);

        model.addAttribute("hideNavLinks", true);
        model.addAttribute("psychotherapistSignupDTO", new PsychotherapistSignupDTO());
        model.addAttribute("successSignup", true);

        return "psychotherapist-signup";
    }

}
