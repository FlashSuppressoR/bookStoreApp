package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateGenreRepository implements GenreRepository {
    private final Session session;
    private static final String FIND_ALL_GENRE_QUERY = "select g from Genre g";

    public HibernateGenreRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Genre> findAll() throws SQLException {
        List<Genre> genreList;
        try {
            genreList = session.createQuery(FIND_ALL_GENRE_QUERY, Genre.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return genreList;
    }

    public Genre findById(Long id) {
        return session.find(Genre.class, id);
    }

    @Override
    public Genre add(Genre genre) throws SQLException {
        Genre newGenre;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newGenreId = (Integer) session.save("Genre", genre);
            newGenre = session.find(Genre.class, newGenreId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the Genre", ex);
        }
        return newGenre;
    }

    @Override
    public void addAll(List<Genre> genres) throws SQLException {
        try {
            for (Genre genre : genres) {
                session.save(genre);
            }
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the genre", ex);
        }
    }

    @Override
    public Genre findById(int id) throws SQLException {
        Genre genre;
        try {
            genre = session.find(Genre.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find Genre", ex);
        }
        return genre;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Genre genre = session.find(Genre.class, id);

            if (genre != null) {
                session.delete(genre);
                result = (null == session.find(Genre.class, id));
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Can not remove 'Genre'", ex);
        }
        return result;
    }

}
