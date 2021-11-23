package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.entity.dto.ReviewDTO;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import com.flashsuppressor.java.lab.service.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public ReviewDTO findById(int id) {
        return convertToReviewDTO(repository.findById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReviewDTO> findAll() {
        return repository.findAll().stream().map(this::convertToReviewDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO create(ReviewDTO reviewDTO) {
        Review newReview = repository.create(convertToReview(reviewDTO));
        return convertToReviewDTO(newReview);
    }

    @Override
    @Transactional
    public List<ReviewDTO> createAll(List<ReviewDTO> reviews) {
        List<ReviewDTO> reviewDTOList = null;
        for (ReviewDTO newReviewDTO : reviews) {
            Review newReview = repository.create(convertToReview(newReviewDTO));
            reviewDTOList.add(convertToReviewDTO(newReview));
        }
        return reviewDTOList;
    }

    @Override
    @Transactional
    public ReviewDTO update(ReviewDTO reviewDTO) {
        ReviewDTO newReviewDTO = null;
        try {
            Review review = repository.findById(reviewDTO.getId());
            review.setMark(reviewDTO.getMark());
            review.setComment(reviewDTO.getComment());
            review.setBook(convertToBook(reviewDTO.getBookDTO()));
            newReviewDTO = convertToReviewDTO(review);
        }
        catch (Exception e){
            System.out.println("Can't update reviewDTO");
        }
        return newReviewDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    private Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    private Review convertToReview(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    private ReviewDTO convertToReviewDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }
}
