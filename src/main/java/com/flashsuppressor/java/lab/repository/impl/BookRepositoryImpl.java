package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.repository.BookRepository;
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
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_BOOKS_QUERY = "select b from Book b ";

    @Override
    public Book findById(Long id) {
        Session session = getSession();
        return session.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        Session session = getSession();
        return session.createQuery(FIND_BOOKS_QUERY, Book.class).list();
    }

    @Override
    public Book create(Book book) {
        Session session = getSession();
        session.save(book);
        return book;
    }

    @Override
    public void createAll(List<Book> books) {
        Session session = getSession();
        for (Book book : books) {
            session.save(book);
        }
    }

    @Override
    public Book update(Book book) {
        Session session = getSession();
        Book updatedBook;
        session.beginTransaction();
        session.update(book);
        updatedBook = session.find(Book.class, book);
        session.getTransaction().commit();

        return updatedBook;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = getSession();
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
