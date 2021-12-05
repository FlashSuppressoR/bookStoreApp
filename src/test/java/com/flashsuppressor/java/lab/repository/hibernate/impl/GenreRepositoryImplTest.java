package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.hibernate.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class GenreRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;
    private final List<Genre> expectedGenres = new ArrayList<>() {{
        add(new Genre(1, "Fantasy"));
        add(new Genre(2, "Horror"));
        add(new Genre(3, "Humor"));
    }};

    @Test
    public void findAllTest() {
        List<Genre> actualGenres = genreRepository.findAll();

        for (int i = 0; i < expectedGenres.size(); i++) {
            assertGenreEquals(expectedGenres.get(i), actualGenres.get(i));
        }
    }

    @Test
    public void findByIdTest() {
        Genre expected = expectedGenres.get(0);
        Genre actual = genreRepository.findById(expected.getId());

        assertGenreEquals(expected, actual);
    }

    @Test
    public void createTest() {
        Genre expectedGenre = new Genre(4, "Love story");
        genreRepository.create(expectedGenre);

        assertEquals(4, genreRepository.findAll().size());
    }

    @Test
    public void createAllTest() {
        List<Genre> expectedList = new ArrayList<>() {{
            add(new Genre(4, "Ballad"));
            add(new Genre(5, "Thriller"));
        }};
        List<Genre> actualList = new ArrayList<>() {{
            add(new Genre(null, "Ballad"));
            add(new Genre(null, "Thriller"));
        }};
        genreRepository.createAll(actualList);

        for (int i = 0; i < expectedList.size(); i++) {
            assertGenreEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void updateTest() {
        Genre expectedGenre = new Genre(3, "Max Ew");
        Genre actualGenre = genreRepository.update(expectedGenre);

        assertGenreEquals(expectedGenre, actualGenre);
    }

    @Test
    public void deleteByIdTest() {
        int genreId = 1;

        assertTrue(genreRepository.deleteById(genreId));
    }

    private void assertGenreEquals(Genre expectedGenre, Genre actualGenre) {
        assertEquals(expectedGenre.getId(), actualGenre.getId());
        assertEquals(expectedGenre.getName(), actualGenre.getName());
    }
}