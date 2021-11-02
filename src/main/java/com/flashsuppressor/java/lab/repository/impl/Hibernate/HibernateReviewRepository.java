package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import org.hibernate.Session;

import java.util.List;

public class HibernateReviewRepository implements ReviewRepository {
    private final Session session;
    private static final String FIND_ALL_REVIEWS_QUERY = "select r from Review r";

    public HibernateReviewRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Review> findAll() {

       return session.createQuery(FIND_ALL_REVIEWS_QUERY, Review.class).list();
    }

    @Override
    public Review create(Review review) {
        Review newReview;
        session.beginTransaction();
        Integer newReviewId = (Integer) session.save(review);
        newReview = session.find(Review.class, newReviewId);
        session.getTransaction().commit();

        return newReview;
    }

    @Override
    public void createAll(List<Review> reviews) {
        for (Review review : reviews) {
            session.save(review);
        }
    }

    @Override
    public Review update(Review review) {
        Review updatedReview;
        session.beginTransaction();
        session.update(review);
        updatedReview = session.find(Review.class, review.getId());
        session.getTransaction().commit();

        return updatedReview;
    }

    @Override
    public boolean deleteById(int id) {
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
