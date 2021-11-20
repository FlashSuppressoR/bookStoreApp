package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.entity.dto.ReviewDTO;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import com.flashsuppressor.java.lab.service.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public List<ReviewDTO> findAll() {
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        List<Review> reviews = repository.findAll();
        if (reviews != null && reviews.size() > 0) {
            reviewDTOs = reviews.stream().map(this::convertToReviewDTO).collect(Collectors.toList());
        }
        return reviewDTOs;
    }

    @Override
    @Transactional
    public void create(Review review) {
        repository.create(review);
    }

    @Override
    @Transactional
    public void createAll(List<Review> reviews) {
        for (Review review : reviews) {
            repository.create(review);
        }
    }

    @Override
    @Transactional
    public ReviewDTO update(Review review) {
        ReviewDTO updatedReviewDTO = null;
        Review updatedReview = repository.update(review);
        if (updatedReview != null) {
            updatedReviewDTO = convertToReviewDTO(updatedReview);
        }

        return updatedReviewDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private ReviewDTO convertToReviewDTO(Review review) {

        return modelMapper.map(review, ReviewDTO.class);
    }
}
