package it.unisa.HAYT.servicies;

import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if(user != null){
            var springUser = User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();

            return springUser;
        }


        return null;
    }
}

