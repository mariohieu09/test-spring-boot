package com.example.springproject.dto.response;

import com.example.springproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String phone;
    private Role role;
    private String token;
}
