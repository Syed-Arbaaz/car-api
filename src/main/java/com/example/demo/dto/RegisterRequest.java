package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

   @NotBlank
private String firstName;

@NotBlank
private String lastName;

@Email
@NotBlank
private String email;

@Pattern(
    regexp = "^[0-9]{10}$",
    message = "Phone number must be 10 digits"
)
private String phone;

@Pattern(
    regexp =
    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
    message =
    "Password must contain uppercase, lowercase and number"
)
    private String password;

    private String role;

    // getters & setters
}
