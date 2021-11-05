package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernateAuthorRepository implements AuthorRepository {
    private final Session session;
    private static final String FIND_AUTHORS_QUERY = "select a from  Author a";

    @Autowired
    public HibernateAuthorRepository(Session session) {
        this.session = session;
    }

    @Override
    public Author findById(int id) {

        return session.find(Author.class, id);
    }

    @Override
    public List<Author> findAll() {

        return session.createQuery(FIND_AUTHORS_QUERY, Author.class).list();
    }

    @Override
    public void create(Author author) {
        session.save(author);
    }

    @Override
    public void createAll(List<Author> authors) {
        for (Author author : authors) {
            session.save(author);
        }
    }

    @Override
    public Author update(Author author) {
        Author updatedAuthor;
        session.beginTransaction();
        session.update(author);
        updatedAuthor = session.find(Author.class, author.getId());
        session.getTransaction().commit();

        return updatedAuthor;
    }

    @Override
    public boolean deleteById(int id) {
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
