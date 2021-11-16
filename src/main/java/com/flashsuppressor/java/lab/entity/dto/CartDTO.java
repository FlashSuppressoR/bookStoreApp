package com.flashsuppressor.java.lab.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private int id;
    private CustomerDTO customerDTO;
    private long bookId;
    private int bookCounter;
}
