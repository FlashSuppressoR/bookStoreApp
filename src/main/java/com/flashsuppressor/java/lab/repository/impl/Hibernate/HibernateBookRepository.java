package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.repository.BookRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernateBookRepository implements BookRepository {
    private final Session session;
    private static final String FIND_BOOKS_QUERY = "select b from Book b ";

    @Autowired
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
    public void create(Book book) {
        session.save(book);
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
    public boolean deleteById(Long id) throws SQLException {
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
