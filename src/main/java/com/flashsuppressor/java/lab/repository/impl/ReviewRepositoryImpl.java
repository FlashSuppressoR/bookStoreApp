package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private static final String FIND_ALL_REVIEWS_QUERY = "select r from Review r";

    private final EntityManager entityManager;

    @Override
    public Review findById(int id){

        return entityManager.find(Review.class, id);
    }

    @Override
    public List<Review> findAll() {

       return entityManager.createQuery(FIND_ALL_REVIEWS_QUERY, Review.class).getResultList();
    }

    @Override
    public Review create(Review review) {
        Session session = entityManager.unwrap(Session.class);
        Integer newReviewId = (Integer) session.save("Review", review);

        return session.find(Review.class, newReviewId);
    }

    @Override
    public List<Review> createAll(List<Review> reviews) {
        List<Review> newList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        for (Review review : reviews) {
            Integer newReviewId = (Integer) session.save("Review", review);
            newList.add(session.find(Review.class, newReviewId));
        }

        return newList;
    }

    @Override
    public Review update(Review review) {
        Session session = entityManager.unwrap(Session.class);
        Review updatedReview;
        session.beginTransaction();
        session.update(review);
        updatedReview = session.find(Review.class, review);
        session.getTransaction().commit();

        return updatedReview;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        boolean result;
        session.beginTransaction();
        Review review = session.find(Review.class, id);
        if (review != null) {
            session.delete(review);
            result = (null == session.find(Review.class, id));
        } else {
            result = false;
        }
        session.getTransaction().commit();

        return result;
    }
}
