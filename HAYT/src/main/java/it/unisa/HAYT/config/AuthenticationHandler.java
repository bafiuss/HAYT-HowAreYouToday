package it.unisa.HAYT.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Collection;

public class AuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
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

