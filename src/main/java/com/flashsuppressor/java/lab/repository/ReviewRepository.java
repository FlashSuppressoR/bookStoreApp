package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewRepository {

    List<Review> findAll();

    Review create(Review review) throws SQLException;

    void createAll(List<Review> reviews);

    Review update(Review review) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
