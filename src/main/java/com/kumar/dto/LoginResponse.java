package com.kumar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login response with JWT token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String tokenType;
    private String username;
    private long expiresIn;

    public LoginResponse(String token, String username, long expiresIn) {
        this.token = token;
        this.tokenType = "Bearer";
        this.username = username;
        this.expiresIn = expiresIn;
    }
}

