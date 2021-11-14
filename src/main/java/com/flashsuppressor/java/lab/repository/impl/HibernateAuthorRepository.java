package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class HibernateAuthorRepository implements AuthorRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_AUTHORS_QUERY = "select a from  Author a";

    @Autowired
    public HibernateAuthorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Author findById(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.find(Author.class, id);
    }

    @Override
    public List<Author> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_AUTHORS_QUERY, Author.class).list();
    }

    @Override
    public Author create(Author author){
        Session session = sessionFactory.getCurrentSession();
        session.save(author);
        return author;
    }

    @Override
    public void createAll(List<Author> authors){
        Session session = sessionFactory.getCurrentSession();
        for (Author author : authors) {
            session.save(author);
        }
    }

    @Override
    public Author update(Author author){
        Session session = sessionFactory.getCurrentSession();
        Author updatedAuthor;
        session.beginTransaction();
        session.update(author);
        updatedAuthor = session.find(Author.class, author.getId());
        session.getTransaction().commit();

        return updatedAuthor;
    }

    @Override
    public boolean deleteById(int id){
        Session session = sessionFactory.getCurrentSession();
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
