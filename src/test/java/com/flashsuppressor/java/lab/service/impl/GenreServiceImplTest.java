package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.repository.data.GenreRepository;
import com.flashsuppressor.java.lab.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GenreServiceImplTest {

    @InjectMocks
    private GenreService service;
    @Mock
    private GenreRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private List<GenreDTO> mockGenresList;

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findByIdTest() {
        //given
        int genreID = 1;
        Genre genre = Genre.builder().id(genreID).name("Test Genre").build();
        GenreDTO expectedGenreDTO = GenreDTO.builder().id(genreID).name("Test Genre").build();
        //when
        when(repository.getById(genreID)).thenReturn(genre);
        when(modelMapper.map(genre, GenreDTO.class)).thenReturn(expectedGenreDTO);
        GenreDTO actualGenreDTO = service.findById(genreID);
        //then
        assertEquals(expectedGenreDTO, actualGenreDTO);
    }

    @Test
    void findAllTest() {
        //given
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Genre(), new Genre()));
        //when
        int actualSize = service.findAll(pageable).getSize();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        GenreDTO genreDTO = GenreDTO.builder().id(4).name("New Genre").build();
        Genre genre = Genre.builder().id(4).name("New Genre").build();
        when(modelMapper.map(genreDTO, Genre.class)).thenReturn(genre);
        when(modelMapper.map(genre, GenreDTO.class)).thenReturn(genreDTO);
        when(repository.save(genre)).thenReturn(genre);
        //when
        GenreDTO actualGenreDTO = service.create(genreDTO);
        //then
        assertAll(() -> assertEquals(genre.getId(), actualGenreDTO.getId()),
                () -> assertEquals(genre.getName(), actualGenreDTO.getName()));
    }

    @Test
    void createAllTest() {
        //given
        List<GenreDTO> listDTO = new ArrayList<>() {{
            add(GenreDTO.builder().id(4).name("First Genre").build());
            add(GenreDTO.builder().id(5).name("Second Genre").build());
        }};
        when(mockGenresList.get(0)).thenReturn(listDTO.get(0));
        when(mockGenresList.get(1)).thenReturn(listDTO.get(1));
        //when
        List<GenreDTO> createList = new ArrayList<>() {{
            add(mockGenresList.get(0));
            add(mockGenresList.get(1));
        }};
        List<GenreDTO> genreDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), genreDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getName(), genreDTOList.get(0).getName()),
                () -> assertEquals(createList.get(1).getId(), genreDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getName(), genreDTOList.get(1).getName()));
    }

    @Test
    void updateTest() {
        //given
        int genreId = 1;
        String newName = "Updated Genre";
        GenreDTO genreDTO = GenreDTO.builder().id(genreId).name(newName).build();
        Genre genre = Genre.builder().id(genreId).name(newName).build();
        when(repository.getById(genreId)).thenReturn(genre);
        when(modelMapper.map(genre, GenreDTO.class)).thenReturn(genreDTO);
        when(repository.getById(genreId)).thenReturn(genre);
        //when
        GenreDTO actualUpdatedGenre = service.update(genreDTO);
        // then
        assertAll(() -> assertEquals(genreId, actualUpdatedGenre.getId()),
                () -> assertEquals(newName, actualUpdatedGenre.getName())
        );
    }

    @Test
    void deleteByIdTest() {
        //given
        int validId = 1;
        //when
        repository.deleteById(validId);
        //then
        assertTrue(repository.findById(validId).isEmpty());
    }
}
