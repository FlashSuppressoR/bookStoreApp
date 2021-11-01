package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewRepository {

    List<Review> findAll() throws SQLException;

    Review add(Review review) throws SQLException;

    void addAll(List<Review> reviews) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
