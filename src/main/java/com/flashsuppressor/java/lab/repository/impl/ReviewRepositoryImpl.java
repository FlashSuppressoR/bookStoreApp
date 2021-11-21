package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_ALL_REVIEWS_QUERY = "select r from Review r";

    @Override
    public List<Review> findAll() {
        Session session = getSession();
       return session.createQuery(FIND_ALL_REVIEWS_QUERY, Review.class).list();
    }

    @Override
    public void create(Review review) {
        Session session = getSession();
        session.save(review);
    }

    @Override
    public void createAll(List<Review> reviews) {
        Session session = getSession();
        for (Review review : reviews) {
            session.save(review);
        }
    }

    @Override
    public Review update(Review review) {
        Session session = getSession();
        Review updatedReview;
        session.beginTransaction();
        session.update(review);
        updatedReview = session.find(Review.class, review);
        session.getTransaction().commit();

        return updatedReview;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = getSession();
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
