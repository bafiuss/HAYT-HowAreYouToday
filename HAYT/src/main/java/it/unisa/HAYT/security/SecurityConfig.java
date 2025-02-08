package it.unisa.HAYT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        // Permetti solo GET per le pagine HTML visibili agli utenti
                        .requestMatchers(HttpMethod.GET,
                                "/",
                                "/index",
                                "/signup",
                                "/login",
                                "/psychologist-signup").permitAll()

                        // Blocca le richieste GET dirette a "/patient-signup" (non visibile via URL)
                        .requestMatchers(HttpMethod.GET,
                                "/patient-signup",
                                "/patient-login",
                                "/patient-home").denyAll()

                        // Permetti POST per "/patient-signup" (solo l'invio del form)
                        .requestMatchers(HttpMethod.POST,
                                "/patient-signup",
                                "/patient-login",
                                "/patient-home").permitAll()

                        // Permetti accesso alle risorse statiche
                        .requestMatchers(
                                "/scripts/**",
                                "/styles/**",
                                "/images/**").permitAll()

                        // Tutte le altre richieste devono essere autenticate
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
