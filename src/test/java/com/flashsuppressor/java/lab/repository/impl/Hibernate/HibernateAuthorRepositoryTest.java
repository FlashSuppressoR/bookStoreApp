package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateAuthorRepositoryTest extends BaseRepositoryTest {
    AuthorRepository authorRepository;
    private final List<Author> expectedAuthors;

    public HibernateAuthorRepositoryTest() {
        super();
        this.authorRepository = new HibernateAuthorRepository(getSessionFactory().openSession());
        expectedAuthors = new ArrayList<>() {{
            add(new Author(1, "Bred Dee"));
            add(new Author(2, "John Serb"));
            add(new Author(3, "Alex Green"));
        }};
    }

    @Test
    public void findAllTest() {
        List<Author> actualAuthors = authorRepository.findAll();
        assertEquals(expectedAuthors.size(), actualAuthors.size());

        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertAuthorEquals(expectedAuthors.get(i), actualAuthors.get(i));
        }
    }

    @Test
    public void findByIdTest() throws SQLException {
        Author expectedAuthor = expectedAuthors.get(1);
        Author actualAuthor = authorRepository.findById(1);

        assertAuthorEquals(expectedAuthor, actualAuthor);
    }

    @Test
    public void createTest() throws SQLException {
        Author expectedAuthor = new Author(4, "Roi Bard");
        authorRepository.create(expectedAuthor);

        assertEquals(4, authorRepository.findAll().size());
    }

    @Test
    public void createAllTest() throws SQLException {
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
    public void updateTest() throws SQLException {
        Author expectedUser = new Author(3, "Max Ew");
        Author actualAuthor = authorRepository.update(expectedUser);

        assertAuthorEquals(expectedUser, actualAuthor);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        int authorId = 1;

        assertTrue(authorRepository.deleteById(authorId));
    }

    private void assertAuthorEquals(Author expectedAuthor, Author actualAuthor) {
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
    }
}