package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.hibernate.PublisherRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Deprecated
public class PublisherRepositoryImpl implements PublisherRepository {
    private static final String FIND_ALL_PUBLISHERS_QUERY = "select p from Publisher p";

    private final EntityManager entityManager;

    @Override
    public List<Publisher> findAll() {

        return entityManager.createQuery(FIND_ALL_PUBLISHERS_QUERY, Publisher.class).getResultList();
    }

    @Override
    public Publisher findById(int id) {

        return entityManager.find(Publisher.class, id);
    }

    @Override
    public Publisher create(Publisher publisher) {
        Session session = entityManager.unwrap(Session.class);
        Integer newPublisherId = (Integer) session.save("Publisher", publisher);

        return session.find(Publisher.class, newPublisherId);
    }

    @Override
    public List<Publisher> createAll(List<Publisher> publishers) {
        List<Publisher> newList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        for (Publisher publisher : publishers) {
            Integer newPublisherId = (Integer) session.save("Publisher", publisher);
            newList.add(session.find(Publisher.class, newPublisherId));
        }

        return newList;
    }

    @Override
    public Publisher update(Publisher publisher) {
        Session session = entityManager.unwrap(Session.class);
        Publisher updatedPublisher;
        session.beginTransaction();
        session.update(publisher);
        updatedPublisher = session.find(Publisher.class, publisher);
        session.getTransaction().commit();

        return updatedPublisher;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
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
