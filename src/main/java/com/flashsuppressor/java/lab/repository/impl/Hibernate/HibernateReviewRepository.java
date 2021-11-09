package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
public class HibernateReviewRepository implements ReviewRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_REVIEWS_QUERY = "select r from Review r";

    @Autowired
    public HibernateReviewRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Review> findAll() {
        Session session = sessionFactory.getCurrentSession();
       return session.createQuery(FIND_ALL_REVIEWS_QUERY, Review.class).list();
    }

    @Override
    public void create(Review review) {

        Session session = sessionFactory.getCurrentSession();session.save(review);
    }

    @Override
    public void createAll(List<Review> reviews) {
        Session session = sessionFactory.getCurrentSession();
        for (Review review : reviews) {
            session.save(review);
        }
    }

    @Override
    public Review update(Review review) {
        Session session = sessionFactory.getCurrentSession();
        Review updatedReview;
        session.beginTransaction();
        session.update(review);
        updatedReview = session.find(Review.class, review.getId());
        session.getTransaction().commit();

        return updatedReview;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
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
