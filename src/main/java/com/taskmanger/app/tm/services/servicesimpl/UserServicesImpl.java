package com.taskmanger.app.tm.services.servicesimpl;

import com.taskmanger.app.tm.entity.Role;
import com.taskmanger.app.tm.entity.User;
import com.taskmanger.app.tm.repository.RoleRepository;
import com.taskmanger.app.tm.repository.UserRepository;
import com.taskmanger.app.tm.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServicesImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Set<Role> roles = Collections.singleton(roleRepository.findByRoleName("USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return user;
    }
}
