package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.entity.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> findAll();

    void create(Review review);

    void createAll(List<Review> reviews);

    ReviewDTO update(Review review);

    boolean deleteById(int id);
}
