package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateBookRepository implements BookRepository {
    private final Session session;
    private static final String FIND_BOOKS_QUERY = "select b from Book b ";

    public HibernateBookRepository(Session session) {
        this.session = session;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Book book = session.find(Book.class, id);

            if (book != null) {
                session.delete(book);
                result = (null == session.find(Book.class, id));
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Can not remove 'Book'", ex);
        }
        return result;
    }

    @Override
    public Book findById(Long id) throws SQLException {
        Book book;
        try {
            book = session.find(Book.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find Book", ex);
        }
        return book;
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> bookList;
        try {
            bookList = session.createQuery(FIND_BOOKS_QUERY, Book.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return bookList;
    }

    @Override
    public Book add(Book book) throws SQLException {
        Book newBook;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newBookId = (Integer) session.save("Book", book);
            newBook = session.find(Book.class, newBookId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the Book", ex);
        }
        return newBook;
    }

    @Override
    public void addAll(List<Book> books) throws SQLException {
        try {
            for (Book book : books) {
                session.save(book);
            }
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the book", ex);
        }
    }

    @Override
    public Book update(Book book) throws SQLException {
        Book updatedBook;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update("Book", book);
            updatedBook = session.find(Book.class, book.getId());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return updatedBook;
    }

}
