package com.flashsuppressor.java.lab.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private int id;
    private int mark;
    private String comment;
    private BookDTO bookDTO;
}
