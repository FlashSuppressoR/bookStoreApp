package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private static final String FIND_AUTHORS_QUERY = "select a from  Author a";
    private final EntityManager entityManager;

    @Override
    public Author findById(int id){

        return entityManager.find(Author.class, id);
    }

    @Override
    public List<Author> findAll() {

        return entityManager.createQuery(FIND_AUTHORS_QUERY, Author.class).getResultList();
    }

    @Override
    public Author create(Author author){
        Session session = entityManager.unwrap(Session.class);
        Integer newUserId = (Integer) session.save("Author", author);

        return session.find(Author.class, newUserId);
    }

    @Override
    public List<Author> createAll(List<Author> authors){
        List<Author> newList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        for (Author author : authors) {
            Integer newUserId = (Integer) session.save("Author", author);
            newList.add(session.find(Author.class, newUserId));
        }

        return newList;
    }

    @Override
    public Author update(Author author){
        Session session = entityManager.unwrap(Session.class);
        Author updatedAuthor;
        session.beginTransaction();
        session.update(author);
        updatedAuthor = session.find(Author.class, author);
        session.getTransaction().commit();

        return updatedAuthor;
    }

    @Override
    public boolean deleteById(int id){
        boolean result;
        Author author = entityManager.find(Author.class, id);
        if (author != null) {
            entityManager.remove(author);
            result = entityManager.find(Author.class, id) == null;
        } else {
            result = false;
        }

        return result;
    }
}
