package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.GenreRepository;
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
public class GenreRepositoryImpl implements GenreRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_ALL_GENRE_QUERY = "select g from Genre g";

    @Override
    public List<Genre> findAll() {
        Session session = getSession();
        return session.createQuery(FIND_ALL_GENRE_QUERY, Genre.class).list();
    }

    public Genre findById(Long id) {
        Session session = getSession();
        return session.find(Genre.class, id);
    }

    @Override
    public Genre findById(int id) {
        Session session = getSession();
        return session.find(Genre.class, id);
    }

    @Override
    public Genre create(Genre genre) {
        Session session = getSession();
        session.save(genre);
        return genre;
    }

    @Override
    public void createAll(List<Genre> genres) {
        Session session = getSession();
        for (Genre genre : genres) {
            session.save(genre);
        }
    }

    @Override
    public Genre update(Genre genre) {
        Session session = getSession();
        Genre updatedGenre;
        session.beginTransaction();
        session.update(genre);
        updatedGenre = session.find(Genre.class, genre);
        session.getTransaction().commit();

        return updatedGenre;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = getSession();
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
