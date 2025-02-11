package it.unisa.HAYT.config;

import it.unisa.HAYT.entities.UserEntity;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;

@Configuration
public class CustomAccessFilter extends HttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        UserEntity user = (UserEntity) httpServletRequest.getSession().getAttribute("user");
        String role = (String) httpServletRequest.getSession().getAttribute("role");

        boolean isLogged = false;
        boolean isPatient = false;
        boolean isPsychologist = false;

        if(user != null){
            isLogged = true;

            if("PATIENT".equals(role)){
                isPatient = true;
            }
            if("PSYCHOLOGIST".equals(role)){
                isPsychologist = true;
            }
        }

        String path = httpServletRequest.getServletPath();
        if( (!isLogged) && (
                //checks for general pages
                path.contains("/patient-home") ||

                //checks for post mapping
                path.contains("/logout")

        )){
            System.out.println("NON SEI LOGGATO");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index");
            return;
        }

        if( (isLogged) && (
                //checks for general pages
                path.contains("/login") || path.contains("/signup") ||
                //checks for post mapping
                path.contains("/patient-login") || path.contains("/patient-signup")

        )){
            System.out.println("SEI LOGGATO");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index");
            return;
        }

        chain.doFilter(request, response);

    }

}
