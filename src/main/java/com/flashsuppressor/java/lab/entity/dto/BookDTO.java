package com.flashsuppressor.java.lab.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private long id;
    private String name;
    private double price;
    private int publisherId;
    private int genreId;
}
