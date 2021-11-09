package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.entity.dto.ReviewDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> findAll() throws ServiceException;

    void create(Review review) throws ServiceException;

    void createAll(List<Review> reviews) throws ServiceException;

    ReviewDTO update(Review review) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
