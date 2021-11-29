package com.flashsuppressor.java.lab.repository.hibernate;

import com.flashsuppressor.java.lab.entity.Review;

import java.util.List;

@Deprecated
public interface ReviewRepository {

    Review findById(int id);

    List<Review> findAll();

    Review create(Review review);

    List<Review> createAll(List<Review> reviews);

    Review update(Review review);

    boolean deleteById(int id);
}
