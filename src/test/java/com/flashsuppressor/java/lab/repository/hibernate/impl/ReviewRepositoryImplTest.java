package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.hibernate.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class ReviewRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    private final List<Review> expectedReviews = new ArrayList<>() {{
        add(new Review(1, 5, "Perfect book!", firstIdBook));
        add(new Review(2, 3, "So-so", secondIdBook));
        add(new Review(3, 4, "So cool", thirdIdBook));
    }};

    private final Book firstIdBook = (new Book(1L, "Little Bee", 3.22,
            new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0));
    private final Book secondIdBook = (new Book(2L, "Big system Black Sun", 2.33,
            new Publisher(2, "Minsk prod"), new Genre(2, "Horror"), 0));
    private final Book thirdIdBook = (new Book(3L, "Alex Green", 13.22,
            new Publisher(3, "New Town"), new Genre(3, "Humor"), 0));

    @Test
    public void findAllTest() {
        List<Review> actualReviews = reviewRepository.findAll();

        for (int i = 0; i < expectedReviews.size(); i++) {
            assertReviewEquals(expectedReviews.get(i), actualReviews.get(i));
        }
    }

    @Test
    public void createTest() {
        Review expectedReview = new Review(4, 5, "cool", thirdIdBook);
        reviewRepository.create(expectedReview);

        assertEquals(4, reviewRepository.findAll().size());
    }

    @Test
    public void createAllTest() {
        List<Review> expectedList = new ArrayList<>() {{
            add(new Review(1, 5, "Perfecto!", firstIdBook));
            add(new Review(2, 3, "pfff", firstIdBook));
        }};
        List<Review> actualList = new ArrayList<>() {{
            add(new Review(1, 5, "Perfecto!", firstIdBook));
            add(new Review(2, 3, "pfff", firstIdBook));
        }};
        reviewRepository.createAll(actualList);

        for (int i = 0; i < expectedList.size(); i++) {
            assertReviewEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteByIdTest() {
        int reviewId = 1;

        assertTrue(reviewRepository.deleteById(reviewId));
    }

    private void assertReviewEquals(Review expectedReview, Review actualReview) {
        assertEquals(expectedReview.getId(), actualReview.getId());
        assertEquals(expectedReview.getMark(), actualReview.getMark());
        assertEquals(expectedReview.getComment(), actualReview.getComment());
        assertEquals(expectedReview.getBook(), actualReview.getBook());
    }
}