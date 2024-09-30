package com.spring_react_demo.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring_react_demo.demo.entity.Role;
import com.spring_react_demo.demo.entity.User;
import com.spring_react_demo.demo.repository.RoleRepository;
import com.spring_react_demo.demo.service.UserService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserService userService) {
		return args -> {
			roleRepository.save(new Role(1L, "USER"));
			// User u = new User();
			// u.setFirstName("Jill");
			// u.setLastName("Cassidy");
			// u.setEmail("jill.cassidy@example.com");  // Set email
			// u.setUsername("jillcassidy");  // Ensure the username is also set
			// u.setPassword("StrongPassword1!");  // Set a valid password
			// userService.registerUser(u);
		};
	}

}
