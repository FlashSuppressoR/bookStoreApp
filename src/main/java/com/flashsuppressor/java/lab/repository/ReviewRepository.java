package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Review;

import java.util.List;

public interface ReviewRepository {

    Review findById(int id);

    List<Review> findAll();

    Review create(Review review);

    void createAll(List<Review> reviews);

    Review update(Review review);

    boolean deleteById(int id);
}
