package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.hibernate.GenreRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    private static final String FIND_ALL_GENRE_QUERY = "select g from Genre g";

    private final EntityManager entityManager;

    @Override
    public List<Genre> findAll() {

        return entityManager.createQuery(FIND_ALL_GENRE_QUERY, Genre.class).getResultList();
    }

    @Override
    public Genre findById(int id) {

        return entityManager.find(Genre.class, id);
    }

    @Override
    public Genre create(Genre genre) {
        Session session = entityManager.unwrap(Session.class);
        Integer newGenreId = (Integer) session.save("Genre", genre);

        return session.find(Genre.class, newGenreId);
    }


    @Override
    public List<Genre> createAll(List<Genre> genres) {
        List<Genre> newList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        for (Genre genre : genres) {
            Integer newGenreId = (Integer) session.save("Genre", genre);
            newList.add(session.find(Genre.class, newGenreId));
        }

        return newList;
    }

    @Override
    public Genre update(Genre genre) {
        Session session = entityManager.unwrap(Session.class);
        Genre updatedGenre;
        session.beginTransaction();
        session.update(genre);
        updatedGenre = session.find(Genre.class, genre);
        session.getTransaction().commit();

        return updatedGenre;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
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
