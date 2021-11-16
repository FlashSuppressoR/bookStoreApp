package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
//@AllArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_GENRE_QUERY = "select g from Genre g";

    @Autowired
    public GenreRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Genre> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_GENRE_QUERY, Genre.class).list();
    }

    public Genre findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Genre.class, id);
    }

    @Override
    public Genre findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Genre.class, id);
    }

    @Override
    public void create(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.save(genre);
    }

    @Override
    public void createAll(List<Genre> genres) {
        Session session = sessionFactory.getCurrentSession();
        for (Genre genre : genres) {
            session.save(genre);
        }
    }

    @Override
    public Genre update(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        Genre updatedGenre;
        session.beginTransaction();
        session.update(genre);
        updatedGenre = session.find(Genre.class, genre);
        session.getTransaction().commit();

        return updatedGenre;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        boolean result;
        session.beginTransaction();
        Genre genre = session.find(Genre.class, id);
        if (genre != null) {
            session.delete(genre);
            result = (null == session.find(Genre.class, id));
        } else {
            result = false;
        }
        session.getTransaction().commit();

        return result;
    }
}
