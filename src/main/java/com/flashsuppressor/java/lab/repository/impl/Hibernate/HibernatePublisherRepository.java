package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernatePublisherRepository implements PublisherRepository {
    private final Session session;
    private static final String FIND_ALL_PUBLISHERS_QUERY = "select p from Publisher p";

    public HibernatePublisherRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Publisher> findAll() throws SQLException {
        List<Publisher> publisherList;
        try {
            publisherList = session.createQuery(FIND_ALL_PUBLISHERS_QUERY, Publisher.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the Publishers", ex);
        }
        return publisherList;
    }

    @Override
    public Publisher findById(int id) throws SQLException {
        Publisher publisher;
        try {
            publisher = session.find(Publisher.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find Publisher", ex);
        }
        return publisher;
    }

    @Override
    public Publisher add(Publisher publisher) throws SQLException {
        Publisher newPublisher;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newPublisherId = (Integer) session.save("Publisher", publisher);
            newPublisher = session.find(Publisher.class, newPublisherId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the Publisher", ex);
        }
        return newPublisher;
    }

    @Override
    public void addAll(List<Publisher> publishers) throws SQLException {
        try {
            for (Publisher publisher : publishers) {
                session.save(publisher);
            }
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the publisher", ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Publisher publisher = session.find(Publisher.class, id);

            if (publisher != null) {
                session.delete(publisher);
                result = (null == session.find(Publisher.class, id));
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Can not remove 'Publisher'", ex);
        }
        return result;
    }
}
