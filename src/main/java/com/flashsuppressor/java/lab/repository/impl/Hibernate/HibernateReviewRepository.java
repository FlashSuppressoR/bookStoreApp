package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateReviewRepository implements ReviewRepository {
    private final Session session;
    private static final String FIND_ALL_REVIEWS_QUERY = "select r from Review r";

    public HibernateReviewRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Review> findAll() throws SQLException {
        List<Review> reviewList;
        try {
            reviewList = session.createQuery(FIND_ALL_REVIEWS_QUERY, Review.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the reviews", ex);
        }
        return reviewList;
    }

    @Override
    public Review add(Review review) throws SQLException {
        Review newReview;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newReviewId = (Integer) session.save("Review", review);
            newReview = session.find(Review.class, newReviewId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the Review", ex);
        }
        return newReview;
    }

    @Override
    public void addAll(List<Review> reviews) throws SQLException {
        try {
            for (Review review : reviews) {
                session.save(review);
            }
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the review", ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Review review = session.find(Review.class, id);

            if (review != null) {
                session.delete(review);
                result = (null == session.find(Review.class, id));
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Can not remove 'Review'", ex);
        }
        return result;
    }
}
