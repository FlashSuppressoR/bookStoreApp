package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernatePublisherRepository implements PublisherRepository {
    private final Session session;
    private static final String FIND_ALL_PUBLISHERS_QUERY = "select p from Publisher p";

    @Autowired
    public HibernatePublisherRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Publisher> findAll() {

        return session.createQuery(FIND_ALL_PUBLISHERS_QUERY, Publisher.class).list();
    }

    @Override
    public Publisher findById(int id) {

        return session.find(Publisher.class, id);
    }

    @Override
    public void create(Publisher publisher) {
       session.save(publisher);
    }

    @Override
    public void createAll(List<Publisher> publishers) {
        for (Publisher publisher : publishers) {
            session.save(publisher);
        }
    }

    @Override
    public Publisher update(Publisher publisher) {
        Publisher updatedPublisher;
        session.beginTransaction();
        session.update(publisher);
        updatedPublisher = session.find(Publisher.class, publisher.getId());
        session.getTransaction().commit();

        return updatedPublisher;
    }

    @Override
    public boolean deleteById(int id) {
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
