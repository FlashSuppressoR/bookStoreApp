package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class AuthorRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    private AuthorRepositoryImpl authorRepository;
    //given
    private final List<Author> expectedAuthors = new ArrayList<>() {{
        add(new Author(1, "Bred Dee"));
        add(new Author(2, "John Serb"));
        add(new Author(3, "Alex Green"));
    }};

    @Test
    public void findAllTest() {
        //when
        List<Author> actualAuthors = authorRepository.findAll();
        //then
        assertEquals(expectedAuthors.size(), actualAuthors.size());
        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertAuthorEquals(expectedAuthors.get(i), actualAuthors.get(i));
        }
    }

    @Test
    public void findByIdTest() {
        //given
        Author expectedAuthor = expectedAuthors.get(0);
        //when
        Author actualAuthor = authorRepository.findById(1);
        //then
        assertAuthorEquals(expectedAuthor, actualAuthor);
    }

    @Test
    public void createTest() {
        //given
        Author expectedAuthor = new Author(4, "Roi Bard");
        //when
        authorRepository.create(expectedAuthor);
        //then
        assertEquals(4, authorRepository.findAll().size());
    }

    @Test
    public void createAllTest() {
        //given
        List<Author> expectedList = new ArrayList<>() {{
            add(new Author(4, "Alexandrod"));
            add(new Author(5, "Bred Eqwex"));
        }};
        List<Author> actualList = new ArrayList<>() {{
            add(new Author(null, "Alexandrod"));
            add(new Author(null, "Bred Eqwex"));
        }};
        //when
        authorRepository.createAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertAuthorEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void updateTest() {
        //given
        Author expectedUser = new Author(3, "Max Ew");
        //when
        Author actualAuthor = authorRepository.update(expectedUser);
        //then
        assert actualAuthor != null;
        assertAuthorEquals(expectedUser, actualAuthor);
    }

    @Test
    public void deleteByIdTest() {
        //given
        int authorId = 1;
        //then
        assertTrue(authorRepository.deleteById(authorId));
    }

    private void assertAuthorEquals(Author expectedAuthor, Author actualAuthor) {
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
    }
}