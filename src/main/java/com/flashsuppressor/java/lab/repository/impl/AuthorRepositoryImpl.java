package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
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
public class AuthorRepositoryImpl implements AuthorRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_AUTHORS_QUERY = "select a from  Author a";

    @Override
    public Author findById(int id){
        Session session = getSession();
        return session.find(Author.class, id);
    }

    @Override
    public List<Author> findAll() {
        Session session = getSession();
        return session.createQuery(FIND_AUTHORS_QUERY, Author.class).list();
    }

    @Override
    public Author create(Author author){
        Session session = getSession();
        session.save(author);
        return author;
    }

    @Override
    public void createAll(List<Author> authors){
        Session session = getSession();
        for (Author author : authors) {
            session.save(author);
        }
    }

    @Override
    public Author update(Author author){
        Session session = getSession();
        Author updatedAuthor;
        session.beginTransaction();
        session.update(author);
        updatedAuthor = session.find(Author.class, author);
        session.getTransaction().commit();

        return updatedAuthor;
    }

    @Override
    public boolean deleteById(int id){
        Session session = getSession();
        boolean result = false;
        session.beginTransaction();
        Author author = session.find(Author.class, id);
        if (author != null) {
            session.delete(author);
            result = session.find(Author.class, id) == null;
        }
        session.getTransaction().commit();

        return result;
    }
}
