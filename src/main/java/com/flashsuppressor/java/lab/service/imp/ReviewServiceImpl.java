package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.entity.dto.ReviewDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import com.flashsuppressor.java.lab.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(@Qualifier("hibernateReviewRepository")
                                       ReviewRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewDTO> findAll() {
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        List<Review> reviews = repository.findAll();
        if (reviews != null && reviews.size() > 0) {
            reviewDTOs = reviews.stream().map(this::convertToReviewDTO).collect(Collectors.toList());
        }
        return reviewDTOs;
    }

    @Override
    public void create(Review review) throws ServiceException {
        try {
            repository.create(review);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createAll(List<Review> reviews) {
        try {
            for (Review review : reviews){
                repository.create(review);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ReviewDTO update(Review review) throws ServiceException {
        ReviewDTO updatedReviewDTO = null;
        try {
            Review updatedReview = repository.update(review);
            if (updatedReview != null) {
                updatedReviewDTO = convertToReviewDTO(updatedReview);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedReviewDTO;
    }

    @Override
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException | SQLException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private ReviewDTO convertToReviewDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }
}
