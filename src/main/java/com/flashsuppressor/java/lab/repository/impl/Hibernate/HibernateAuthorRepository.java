package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateAuthorRepository implements AuthorRepository {
    private final Session session;
    private static final String FIND_AUTHORS_QUERY = "select a from Author a ";

    public HibernateAuthorRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Author> findAll() throws SQLException {
        List<Author> authorList;
        try {
            authorList = session.createQuery(FIND_AUTHORS_QUERY, Author.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return authorList;
    }

    @Override
    public Author update(Author author) throws SQLException {
        Author updatedAuthor;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update("Author", author);
            updatedAuthor = session.find(Author.class, author.getId());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return updatedAuthor;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result = false;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Author author = session.find(Author.class, id);
            if (author != null) {
                session.delete(author);
                result = session.find(Author.class, id) == null;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return result;
    }

    @Override
    public Author add(Author author) throws SQLException {
        Author newAuthor;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newAuthorId = (Integer) session.save("Author", author);
            newAuthor = session.find(Author.class, newAuthorId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return newAuthor;
    }

    @Override
    public void addAll(List<Author> authors) throws SQLException {
        try {
            for (Author author : authors) {
                session.save(author);
            }
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
    }

    @Override
    public Author findById(int id) throws SQLException {
        Author author;
        try {
            author = session.find(Author.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find author", ex);
        }
        return author;
    }
}
