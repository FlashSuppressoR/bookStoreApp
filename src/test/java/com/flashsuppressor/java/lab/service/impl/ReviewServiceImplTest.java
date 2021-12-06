package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.data.ReviewRepository;
import com.flashsuppressor.java.lab.service.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl service;
    @Mock
    private ReviewRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private List<ReviewDTO> mockReviewsList;

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findByIdTest() {
        //given
        int reviewID = 1;
        Review review = Review.builder().id(reviewID).mark(5).comment("Cool")
                .book(Book.builder().id(4L).name("TestBook").build()).build();
        ReviewDTO expectedReviewDTO = ReviewDTO.builder().id(reviewID).mark(5).comment("Cool")
                .bookId(4L).build();
        when(repository.getById(reviewID)).thenReturn(review);
        when(modelMapper.map(review, ReviewDTO.class)).thenReturn(expectedReviewDTO);
        //when
        ReviewDTO actualReviewDTO = service.findById(reviewID);
        //then
        assertEquals(expectedReviewDTO, actualReviewDTO);
    }

    @Test
    void findAllTest() {
        //given
        int expectedSize = 2;
        when(repository.findAll()).thenReturn(Arrays.asList(new Review(), new Review()));
        //when
        int actualSize = service.findAll(pageable).getSize();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        Review review = Review.builder().id(4).mark(5).comment("Cool")
                .book(Book.builder().id(4L).name("TestBook").build()).build();
        ReviewDTO expectedReviewDTO = ReviewDTO.builder().id(4).mark(5).comment("Cool")
                .bookId(4L).build();
        when(modelMapper.map(expectedReviewDTO, Review.class)).thenReturn(review);
        when(modelMapper.map(review, ReviewDTO.class)).thenReturn(expectedReviewDTO);
        when(repository.save(review)).thenReturn(review);
        //when
        ReviewDTO actualReviewDTO = service.create(expectedReviewDTO);
        //then
        assertAll(() -> assertEquals(expectedReviewDTO.getId(), actualReviewDTO.getId()),
                () -> assertEquals(expectedReviewDTO.getMark(), actualReviewDTO.getMark()),
                () -> assertEquals(expectedReviewDTO.getComment(), actualReviewDTO.getComment()),
                () -> assertEquals(expectedReviewDTO.getBookId(), actualReviewDTO.getBookId()));
    }

    @Test
    void createAllTest() {
        //given
        List<ReviewDTO> listDTO = new ArrayList<>() {{
            add(ReviewDTO.builder().id(4).mark(4).comment("Cool").bookId(1L).build());
            add(ReviewDTO.builder().id(5).mark(5).comment("Perfecto").bookId(1L).build());
        }};
        when(mockReviewsList.get(0)).thenReturn(listDTO.get(0));
        when(mockReviewsList.get(1)).thenReturn(listDTO.get(1));
        //when
        List<ReviewDTO> createList = new ArrayList<>() {{
            add(mockReviewsList.get(0));
            add(mockReviewsList.get(1));
        }};
        List<ReviewDTO> reviewDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), reviewDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getMark(), reviewDTOList.get(0).getMark()),
                () -> assertEquals(createList.get(0).getComment(), reviewDTOList.get(0).getComment()),
                () -> assertEquals(createList.get(0).getBookId(), reviewDTOList.get(0).getBookId()),
                () -> assertEquals(createList.get(1).getId(), reviewDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getMark(), reviewDTOList.get(1).getMark()),
                () -> assertEquals(createList.get(1).getComment(), reviewDTOList.get(1).getComment()),
                () -> assertEquals(createList.get(1).getBookId(), reviewDTOList.get(1).getBookId()));
    }

    @Test
    void updateTest() {
        //given
        int reviewId = 1;
        String newComment = "Updated Comment";
        Review review = Review.builder().id(4).mark(5).comment("Cool")
                .book(Book.builder().id(4L).name("TestBook").build()).build();
        ReviewDTO expectedReviewDTO = ReviewDTO.builder().id(4).mark(5).comment("Cool").bookId(4L).build();
        when(repository.getById(reviewId)).thenReturn(review);
        when(modelMapper.map(review, ReviewDTO.class)).thenReturn(expectedReviewDTO);
        when(repository.getById(reviewId)).thenReturn(review);
        //when
        ReviewDTO actualUpdatedReview = service.update(expectedReviewDTO);
        // then
        assertAll(() -> assertEquals(reviewId, actualUpdatedReview.getId()),
                () -> assertEquals(newComment, actualUpdatedReview.getComment())
        );
    }

    @Test
    void deleteByIdTest() {
        //given
        int validId = 1;
        //when
        repository.deleteById(validId);
        //then
        assertTrue(repository.findById(validId).isEmpty());
    }
}
