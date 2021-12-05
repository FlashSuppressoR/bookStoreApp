package com.flashsuppressor.java.lab.security.entity;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
