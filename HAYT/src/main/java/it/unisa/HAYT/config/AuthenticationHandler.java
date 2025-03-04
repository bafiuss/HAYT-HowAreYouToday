package it.unisa.HAYT.config;

import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Optional<UserEntity> user = userRepository.findByEmail(email);
        user.ifPresent(userEntity -> session.setAttribute("user", userEntity));

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_PATIENT".equals(role)) {
                redirectUrl = "/patient-dashboard";
                break;
            } else if ("ROLE_PSYCHOLOGIST".equals(role)) {
                redirectUrl = "/psychologist-dashboard";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}

