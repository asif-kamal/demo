package com.spring_react_demo.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_react_demo.demo.dto.UserRegistrationDTO;
import com.spring_react_demo.demo.entity.Role;
import com.spring_react_demo.demo.entity.User;
import com.spring_react_demo.demo.exception.EmailAlreadyTakenException;
import com.spring_react_demo.demo.exception.EmailFailedToSendException;
import com.spring_react_demo.demo.exception.IncorrectVerificationCodeException;
import com.spring_react_demo.demo.exception.UserDoesNotExistException;
import com.spring_react_demo.demo.repository.RoleRepository;
import com.spring_react_demo.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GmailService gmailService;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDTO userRegDTO) {

        User user = new User();
        user.setFirstName(userRegDTO.getFirstName());
        user.setLastName(userRegDTO.getLastName());
        user.setEmail(userRegDTO.getEmail());
        user.setDateOfBirth(userRegDTO.getDateOfBirth());

        String name = user.getFirstName() + user.getLastName();
        Boolean nameTaken = true;
        String tempName = "";

        while (nameTaken) {
            tempName = generateUsername(name);

            if (userRepository.findByUsername(tempName).isEmpty()) {
                nameTaken = false;
            }
        }

        user.setUsername(tempName);

        Set<Role> roles = user.getAuthorities();

        // Check if roles is null, and if so, initialize it
        if (roles == null) {
            roles = new HashSet<>();
        }

        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    private String generateUsername(String name) {
        long generatedNumber = (long) Math.floor(Math.random() * 1_000_000_000);
        return name + generatedNumber;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
    }

    public User updateUser(User user) {

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public void generateUserVerification(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
        user.setVerification(generateVerificationNumber());

        try {
            gmailService.sendEmail(user.getEmail(), "Your verification code",
                    "Here is your verification code: " + user.getVerification());
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailFailedToSendException();
        }
        // userRepository.save(user);
    }

    private Long generateVerificationNumber() {
        return (long) Math.floor(Math.random() * 100_000_000);
    }

    public User verifyEmail(String username, Long code) {
        User user = userRepository.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        if (code.equals(user.getVerification())) {
            user.setEnabled(true);
            user.setVerification(null);
            return userRepository.save(user);
        } else {
            throw new IncorrectVerificationCodeException();
        }
    }

    public User setPassword(String username, String password) {

        User user = userRepository.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
