package com.taskmanger.app.tm.security;

import com.taskmanger.app.tm.entity.Role;
import com.taskmanger.app.tm.entity.User;
import com.taskmanger.app.tm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user==null)
            throw new UsernameNotFoundException("Invalid Credentials");


        return new ExtendUser(
                user, mapToRoles(user.getRoles())
                );
    }

    private Collection<? extends GrantedAuthority> mapToRoles(Set<Role> roles) {

         return roles.stream().map(role -> new SimpleGrantedAuthority(
                role.getRoleName())).collect(Collectors.toSet()
        );
    }
}
