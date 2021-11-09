package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface ReviewRepository {

    List<Review> findAll();

    void create(Review review) throws RepositoryException;

    void createAll(List<Review> reviews);

    Review update(Review review) throws RepositoryException;

    boolean deleteById(int id) throws RepositoryException, SQLException;
}
