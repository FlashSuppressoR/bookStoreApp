package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import org.hibernate.Session;

import java.util.List;

public class HibernateGenreRepository implements GenreRepository {
    private final Session session;
    private static final String FIND_ALL_GENRE_QUERY = "select g from Genre g";

    public HibernateGenreRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Genre> findAll() {

        return session.createQuery(FIND_ALL_GENRE_QUERY, Genre.class).list();
    }

    public Genre findById(Long id) {
        return session.find(Genre.class, id);
    }

    @Override
    public Genre findById(int id) {

        return session.find(Genre.class, id);
    }

    @Override
    public Genre create(Genre genre) {
        Genre newGenre;
        session.beginTransaction();
        Integer newGenreId = (Integer) session.save(genre);
        newGenre = session.find(Genre.class, newGenreId);
        session.getTransaction().commit();

        return newGenre;
    }

    @Override
    public void createAll(List<Genre> genres) {
        for (Genre genre : genres) {
            session.save(genre);
        }
    }

    @Override
    public Genre update(Genre genre) {
        Genre updatedGenre;
        session.beginTransaction();
        session.update(genre);
        updatedGenre = session.find(Genre.class, genre.getId());
        session.getTransaction().commit();

        return updatedGenre;
    }

    @Override
    public boolean deleteById(int id) {
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
