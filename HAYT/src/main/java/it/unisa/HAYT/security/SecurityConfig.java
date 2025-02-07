package it.unisa.HAYT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/",
                                "/index",
                                "/signup",
                                "/login",
                                "/patient-signup",
                                "/psychologist-signup",
                                "/scripts/**",
                                "/styles/**",
                                "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form ->{
                    form.loginPage("/login").permitAll();
                })
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
