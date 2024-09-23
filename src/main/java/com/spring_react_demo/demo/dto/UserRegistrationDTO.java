package com.spring_react_demo.demo.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
}
