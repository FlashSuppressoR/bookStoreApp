package com.flashsuppressor.java.lab.service.dto;

import com.flashsuppressor.java.lab.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
