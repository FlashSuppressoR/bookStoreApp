package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.repository.BookRepository;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class HibernateBookRepository implements BookRepository {
    private final Session session;
    private static final String FIND_BOOKS_QUERY = "select b from Book b ";

    public HibernateBookRepository(Session session) {
        this.session = session;
    }

    @Override
    public Book findById(Long id) throws SQLException {

        return session.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {

        return session.createQuery(FIND_BOOKS_QUERY, Book.class).list();
    }

    @Override
    public Book create(Book book) {
        Book newBook;
        session.beginTransaction();
        Integer newBookId = (Integer) session.save(book);
        newBook = session.find(Book.class, newBookId);
        session.getTransaction().commit();

        return newBook;
    }

    @Override
    public void createAll(List<Book> books) {
        for (Book book : books) {
            session.save(book);
        }
    }

    @Override
    public Book update(Book book) {
        Book updatedBook;
        session.beginTransaction();
        session.update(book);
        updatedBook = session.find(Book.class, book.getId());
        session.getTransaction().commit();

        return updatedBook;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        session.beginTransaction();
        Book book = session.find(Book.class, id);
        if (book != null) {
            session.delete(book);
            result = (null == session.find(Book.class, id));
        } else {
            result = false;
        }
        session.getTransaction().commit();

        return result;
    }
}
