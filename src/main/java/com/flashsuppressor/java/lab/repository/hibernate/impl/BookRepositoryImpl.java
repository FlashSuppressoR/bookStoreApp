package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.repository.hibernate.BookRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Deprecated
public class BookRepositoryImpl implements BookRepository {
    private static final String FIND_BOOKS_QUERY = "select b from Book b ";

    private final EntityManager entityManager;

    @Override
    public Book findById(Long id) {

        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {

        return entityManager.createQuery(FIND_BOOKS_QUERY, Book.class).getResultList();
    }

    @Override
    public Book create(Book book) {
        Session session = entityManager.unwrap(Session.class);
        Integer newBookId = (Integer) session.save("Book", book);

        return session.find(Book.class, newBookId);
    }

    @Override
    public List<Book> createAll(List<Book> books) {
        List<Book> newList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        for (Book book : books) {
            Integer newBookId = (Integer) session.save("Book", book);
            newList.add(session.find(Book.class, newBookId));
        }

        return newList;
    }

    @Override
    public Book update(Book book) {
        Session session = entityManager.unwrap(Session.class);
        Book updatedBook;
        session.beginTransaction();
        session.update(book);
        updatedBook = session.find(Book.class, book);
        session.getTransaction().commit();

        return updatedBook;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = entityManager.unwrap(Session.class);
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
