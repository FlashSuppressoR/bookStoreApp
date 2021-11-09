package com.flashsuppressor.java.lab.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO{
    int id;
    String name;


    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
