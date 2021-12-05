package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
import com.flashsuppressor.java.lab.service.dto.ReviewDTO;
import com.flashsuppressor.java.lab.repository.data.ReviewRepository;
import com.flashsuppressor.java.lab.service.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Override
    public ReviewDTO findById(int id) {
        return convertToReviewDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<ReviewDTO> findAll(Pageable pgb) {
        Page<Review> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToReviewDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public ReviewDTO create(ReviewDTO reviewDTO) {
        Review newReview = repository.save(convertToReview(reviewDTO));
        return convertToReviewDTO(newReview);
    }

    @Override
    @Transactional
    public List<ReviewDTO> createAll(List<ReviewDTO> reviews) {
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (ReviewDTO newReviewDTO : reviews) {
            Review newReview = repository.save(convertToReview(newReviewDTO));
            reviewDTOList.add(convertToReviewDTO(newReview));
        }
        return reviewDTOList;
    }

    @Override
    @Transactional
    public ReviewDTO update(ReviewDTO reviewDTO) {
        ReviewDTO newReviewDTO = null;
        try {
            Review review = repository.getById(reviewDTO.getId());
            review.setMark(reviewDTO.getMark());
            review.setComment(reviewDTO.getComment());
            review.setBook(convertToBook(reviewDTO.getBookDTO()));

            repository.flush();
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
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
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
