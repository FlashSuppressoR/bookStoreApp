package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void findAllTest() throws SQLException {
        //given && when
        List<Author> actualAuthors = authorRepository.findAll();
        //then
        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertAuthorEquals(expectedAuthors.get(i), actualAuthors.get(i));
        }
    }

    @Test
    public void updateTest() throws SQLException {
        //given
        Author expectedUser = new Author(3, "Max Ew");
        //when
        Author actualAuthor = authorRepository.update(expectedUser);
        //then
        assertAuthorEquals(expectedUser, actualAuthor);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        //when
        int authorId = 1;
        //then
        assertTrue(authorRepository.deleteById(authorId));
    }

    @Test
    public void addTest() throws SQLException {
        //given
        Author expectedAuthor = new Author(4, "Roi Bard");
        //when
        Author actualAuthor = authorRepository.add(expectedAuthor);

        //then
        assertAuthorEquals(expectedAuthor, actualAuthor);
    }

    @Test
    public void addAllTest() throws SQLException {
        //given
        List<Author> expectedList = new ArrayList<>() {{
            add(new Author(4, "Alexandrod"));
            add(new Author(5, "Bred Eqwex"));
        }};
        //when
        List<Author> actualList = new ArrayList<>() {{
            add(new Author(null, "Alexandrod"));
            add(new Author(null, "Bred Eqwex"));
        }};
        authorRepository.addAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertAuthorEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void findByIdTest() throws SQLException {
        //given
        Author expectedAuthor = new Author(1, "Bred Dee");
        //when
        Author actualAuthor = authorRepository.findById(1);
        //then
        assertAuthorEquals(expectedAuthor, actualAuthor);
    }

    private void assertAuthorEquals(Author expectedAuthor, Author actualAuthor) {
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
    }
}