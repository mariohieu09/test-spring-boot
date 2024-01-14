package com.example.springproject.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull
    @NotBlank
    @Size(min = 6, max = 15, message = "Username must be over 6 characters long and under 15 characters")
    private String username;
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(?=.*[!@#$%^&*()-_+=])(?=.*[A-Z])(?=.*[0-9]).{6,}$", message = "Password must contains at least 1 uppercase character, 1 number, 1 special character and have at least 6 characters!")
    private String password;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @Pattern(regexp =  "^(\\+84|0)([0-9]{9,10})$")
    private String phone;
}
