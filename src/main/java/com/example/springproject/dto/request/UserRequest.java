package com.example.springproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a request dto (data transfer object) class contains user information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  private String username;
  private String password;
  private String email;
  private String phone;
  private String role;
}