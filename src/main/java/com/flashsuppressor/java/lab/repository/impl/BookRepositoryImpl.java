package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.repository.BookRepository;
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
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_BOOKS_QUERY = "select b from Book b ";

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BOOKS_QUERY, Book.class).list();
    }

    @Override
    public void create(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Override
    public void createAll(List<Book> books) {
        Session session = sessionFactory.getCurrentSession();
        for (Book book : books) {
            session.save(book);
        }
    }

    @Override
    public Book update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book updatedBook;
        session.beginTransaction();
        session.update(book);
        updatedBook = session.find(Book.class, book);
        session.getTransaction().commit();

        return updatedBook;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
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
