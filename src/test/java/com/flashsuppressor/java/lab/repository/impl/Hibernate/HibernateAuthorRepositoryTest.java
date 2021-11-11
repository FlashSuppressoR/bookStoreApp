package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class HibernateAuthorRepositoryTest extends BaseRepositoryTest {

    @Qualifier("hibernateAuthorRepository")
    @Autowired
    AuthorRepository authorRepository;

    private final List<Author> expectedAuthors = new ArrayList<>() {{
        add(new Author(1, "Bred Dee"));
        add(new Author(2, "John Serb"));
        add(new Author(3, "Alex Green"));
    }};

    @Test
    public void findAllTest() throws RepositoryException {
        List<Author> actualAuthors = authorRepository.findAll();
        assertEquals(expectedAuthors.size(), actualAuthors.size());

        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertAuthorEquals(expectedAuthors.get(i), actualAuthors.get(i));
        }
    }

    @Test
    public void findByIdTest() throws RepositoryException {
        Author expectedAuthor = expectedAuthors.get(0);
        Author actualAuthor = authorRepository.findById(1);

        assertAuthorEquals(expectedAuthor, actualAuthor);
    }

    @Test
    public void createTest() throws RepositoryException {
        Author expectedAuthor = new Author(4, "Roi Bard");
        authorRepository.create(expectedAuthor);

        assertEquals(4, authorRepository.findAll().size());
    }

    @Test
    public void createAllTest() throws RepositoryException {
        List<Author> expectedList = new ArrayList<>() {{
            add(new Author(4, "Alexandrod"));
            add(new Author(5, "Bred Eqwex"));
        }};
        List<Author> actualList = new ArrayList<>() {{
            add(new Author(null, "Alexandrod"));
            add(new Author(null, "Bred Eqwex"));
        }};
        authorRepository.createAll(actualList);

        for (int i = 0; i < expectedList.size(); i++) {
            assertAuthorEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void updateTest() throws RepositoryException{
        Author expectedUser = new Author(3, "Max Ew");
        Author actualAuthor = null;
        try {
            actualAuthor = authorRepository.update(expectedUser);
        } catch (RepositoryException | SQLException ex) {
            ex.printStackTrace();
        }

        assert actualAuthor != null;
        assertAuthorEquals(expectedUser, actualAuthor);
    }

    @Test
    public void deleteByIdTest() throws RepositoryException {
        int authorId = 1;

        assertTrue(authorRepository.deleteById(authorId));
    }

    private void assertAuthorEquals(Author expectedAuthor, Author actualAuthor) {
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
    }
}