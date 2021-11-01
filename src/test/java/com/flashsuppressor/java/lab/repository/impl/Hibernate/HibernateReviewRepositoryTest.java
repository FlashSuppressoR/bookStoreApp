package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.*;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateReviewRepositoryTest extends BaseRepositoryTest {
    private final ReviewRepository reviewRepository;
    private final List<Review> expectedReviews;
    private final Book firstIdBook = (new Book( 1L , "Little Bee", 3.22 ,
            new Publisher(1 , "Big Daddy"), new Genre( 1 , "Fantasy"),0));
    private final Book secondIdBook = (new Book( 2L , "Big system Black Sun", 2.33,
            new Publisher(2 , "Minsk prod"), new Genre( 2 , "Horror"),0));
    private final Book thirdIdBook = (new Book( 3L , "Alex Green", 13.22,
            new Publisher(3 , "New Town"), new Genre( 3 , "Humor"),0));

    public HibernateReviewRepositoryTest() {
        super();
        this.reviewRepository = new HibernateReviewRepository(getSessionFactory().openSession());
        expectedReviews = new ArrayList<>() {{
            add(new Review( 1 , 5, "Perfect book!", firstIdBook));
            add(new Review( 2 , 3, "So-so", secondIdBook));
            add(new Review( 3 , 4, "So cool", thirdIdBook));
        }};
    }

    @Test
    public void findAll() throws SQLException {
        //given
        //when
        List<Review> actualReviews = reviewRepository.findAll();
        //then
        for (int i = 0; i < expectedReviews.size(); i++) {
            assertReviewEquals(expectedReviews.get(i), actualReviews.get(i));
        }
    }

    @Test
    public void add() throws SQLException {
        //given
        Review expectedReview = new Review( 4 , 5, "cool", thirdIdBook);
        //when
        Review actualReview = reviewRepository.add(expectedReview);

        //then
        assertReviewEquals(expectedReview, actualReview);
    }

    @Test
    public void addAll() throws SQLException {
        List<Review> expectedList = new ArrayList<>(){{
            add(new Review( 1 , 5, "Perfecto!", firstIdBook));
            add(new Review( 2 , 3, "pfff", firstIdBook));
        }};
        //when
        List<Review> actualList = new ArrayList<>(){{
            add(new Review( 1 , 5, "Perfecto!", firstIdBook));
            add(new Review( 2 , 3, "pfff", firstIdBook));
        }};
        reviewRepository.addAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertReviewEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteById() throws SQLException {
        //when
        int reviewId = 1;
        //then
        assertTrue(reviewRepository.deleteById(reviewId));
    }

    private void assertReviewEquals(Review expectedReview, Review actualReview) {
        assertEquals(expectedReview.getId(), actualReview.getId());
        assertEquals(expectedReview.getMark(), actualReview.getMark());
        assertEquals(expectedReview.getComment(), actualReview.getComment());
        assertEquals(expectedReview.getBook(), actualReview.getBook());
    }
}