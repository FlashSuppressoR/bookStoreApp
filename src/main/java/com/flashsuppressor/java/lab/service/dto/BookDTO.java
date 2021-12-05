package com.flashsuppressor.java.lab.service.dto;

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
    private GenreDTO genreDTO;
    private PublisherDTO publisherDTO;
    private int amount;
}
