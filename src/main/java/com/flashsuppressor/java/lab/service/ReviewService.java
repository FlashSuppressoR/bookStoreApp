package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO findById(int id);

    List<ReviewDTO> findAll();

    ReviewDTO create(ReviewDTO reviewDTO);

    List<ReviewDTO> createAll(List<ReviewDTO> reviews);

    ReviewDTO update(ReviewDTO reviewDTO);

    boolean deleteById(int id);
}
