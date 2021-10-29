package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.flashsuppressor.java.lab.util.HibernateUtil.getSessionFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateGenreRepositoryTest {
    private final GenreRepository genreRepository;
    private final List<Genre> expectedGenres;

    public HibernateGenreRepositoryTest() {
        super();
        this.genreRepository = new HibernateGenreRepository(getSessionFactory().openSession());
        expectedGenres = new ArrayList<>() {{
            add(new Genre( 1 , "Fantasy"));
            add(new Genre( 2 , "Horror" ));
            add(new Genre( 3 , "Humor" ));
        }};
    }

    @Test
    public void findAll() throws SQLException {
        //given
        //when
        List<Genre> actualGenres = genreRepository.findAll();
        //then
        for (int i = 0; i < expectedGenres.size(); i++) {
            assertGenreEquals(expectedGenres.get(i), actualGenres.get(i));
        }
    }

    @Test
    public void findById() throws SQLException {
        //given
        Genre expected = expectedGenres.get(0);
        //when
        Genre actual = genreRepository.findById(expected.getId());
        //then
        assertGenreEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        //given
        Genre expectedGenre = new Genre( 4 ,"Love story");
        //when
        Genre actualGenre = genreRepository.add(expectedGenre);
        //then
        assertGenreEquals(expectedGenre, actualGenre);
    }

    @Test
    public void addAll() throws SQLException {
        //given
        List<Genre> expectedList = new ArrayList<>(){{
            add(new Genre(4, "Ballad"));
            add(new Genre(5,"Thriller"));
        }};
        //when
        List<Genre> actualList = new ArrayList<>(){{
            add(new Genre(null, "Ballad"));
            add(new Genre(null,"Thriller"));
        }};
        genreRepository.addAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertGenreEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteById() throws SQLException {
        //when
        int genreId = 1;
        //then
        assertTrue(genreRepository.deleteById(genreId));
    }

    private void assertGenreEquals(Genre expectedGenre, Genre actualGenre) {
        assertEquals(expectedGenre.getId(), actualGenre.getId());
        assertEquals(expectedGenre.getName(), actualGenre.getName());
    }
}