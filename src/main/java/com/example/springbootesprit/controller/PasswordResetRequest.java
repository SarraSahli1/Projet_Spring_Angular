package com.example.springbootesprit.controller;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String newPassword;
    private String token;

    public String getToken() {
        return token;
    }
}
