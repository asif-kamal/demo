package com.spring_react_demo.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.spring_react_demo.demo.entity.Role;
import com.spring_react_demo.demo.entity.User;
import com.spring_react_demo.demo.repository.RoleRepository;
import com.spring_react_demo.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    public User registerUser(User user) {
        
        Set<Role> roles = user.getAuthorities();

        // Check if roles is null, and if so, initialize it
        if (roles == null) {
            roles = new HashSet<>();
        }

        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        return userRepository.save(user);
    }
}
