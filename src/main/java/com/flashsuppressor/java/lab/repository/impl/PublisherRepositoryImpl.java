package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
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
public class PublisherRepositoryImpl implements PublisherRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_ALL_PUBLISHERS_QUERY = "select p from Publisher p";

    @Override
    public List<Publisher> findAll() {
        Session session = getSession();
        return session.createQuery(FIND_ALL_PUBLISHERS_QUERY, Publisher.class).list();
    }

    @Override
    public Publisher findById(int id) {
        Session session = getSession();
        return session.find(Publisher.class, id);
    }

    @Override
    public Publisher create(Publisher publisher) {
        Session session = getSession();
        session.save(publisher);
        return publisher;
    }

    @Override
    public void createAll(List<Publisher> publishers) {
        Session session = getSession();
        for (Publisher publisher : publishers) {
            session.save(publisher);
        }
    }

    @Override
    public Publisher update(Publisher publisher) {
        Session session = getSession();
        Publisher updatedPublisher;
        session.beginTransaction();
        session.update(publisher);
        updatedPublisher = session.find(Publisher.class, publisher);
        session.getTransaction().commit();

        return updatedPublisher;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = getSession();
        boolean result;
        session.beginTransaction();
        Publisher publisher = session.find(Publisher.class, id);
        if (publisher != null) {
            session.delete(publisher);
            result = (null == session.find(Publisher.class, id));
        } else {
            result = false;
        }
        session.getTransaction().commit();

        return result;
    }
}
