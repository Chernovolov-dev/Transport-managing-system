package ua.nure.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.diploma.configs.SecurityConfig;
import ua.nure.diploma.models.User;
import ua.nure.diploma.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private SecurityConfig securityConfig;

    @Autowired
    public UserService(UserRepository userRepository,SecurityConfig securityConfig) {
        this.securityConfig=securityConfig;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByUserName(username);

        if(user==null) throw new UsernameNotFoundException(String.format("User '%s' not found",username));

        user.setUserPassword(securityConfig.passwordEncoder().encode(user.getUserPassword()));

        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getUserPassword(),getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user){

        List<String> authorities= Collections.singletonList(user.getUserAuthority());

        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
