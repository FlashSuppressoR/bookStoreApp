package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Review;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAll();

    void create(Review review);

    void createAll(List<Review> reviews);

    Review update(Review review);

    boolean deleteById(int id);
}
