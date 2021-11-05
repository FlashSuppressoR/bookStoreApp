package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateGenreRepositoryTest extends BaseRepositoryTest {
    private final GenreRepository genreRepository;
    private final List<Genre> expectedGenres;

    public HibernateGenreRepositoryTest() {
        super();
        this.genreRepository = new HibernateGenreRepository(getSessionFactory().openSession());
        expectedGenres = new ArrayList<>() {{
            add(new Genre(1, "Fantasy"));
            add(new Genre(2, "Horror"));
            add(new Genre(3, "Humor"));
        }};
    }

    @Test
    public void findAll(){
        List<Genre> actualGenres = genreRepository.findAll();

        for (int i = 0; i < expectedGenres.size(); i++) {
            assertGenreEquals(expectedGenres.get(i), actualGenres.get(i));
        }
    }

    @Test
    public void findById() throws SQLException {
        Genre expected = expectedGenres.get(0);
        Genre actual = genreRepository.findById(expected.getId());

        assertGenreEquals(expected, actual);
    }

    @Test
    public void createTest() throws SQLException {
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
    public void updateTest() throws SQLException {
        Genre expectedGenre = new Genre(3, "Max Ew");
        Genre actualGenre = genreRepository.update(expectedGenre);

        assertGenreEquals(expectedGenre, actualGenre);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        int genreId = 1;

        assertTrue(genreRepository.deleteById(genreId));
    }

    private void assertGenreEquals(Genre expectedGenre, Genre actualGenre) {
        assertEquals(expectedGenre.getId(), actualGenre.getId());
        assertEquals(expectedGenre.getName(), actualGenre.getName());
    }
}