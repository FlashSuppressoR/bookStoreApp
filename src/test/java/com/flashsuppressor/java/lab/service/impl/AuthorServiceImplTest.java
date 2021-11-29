package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.repository.data.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @InjectMocks
    private AuthorServiceImpl service;
    @Mock
    private AuthorRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private List<AuthorDTO> mockAuthorsList;

    @Test
    void findByIdTest() {
        int authorID = 1;
        Author author = Author.builder().id(authorID).name("TestAuthor").build();
        AuthorDTO expectedAuthorDTO = AuthorDTO.builder().id(authorID).name("TestAuthor").build();

        when(repository.getById(authorID)).thenReturn(author);
        when(modelMapper.map(author, AuthorDTO.class)).thenReturn(expectedAuthorDTO);
        AuthorDTO actualAuthorDTO = service.findById(authorID);

        assertEquals(expectedAuthorDTO, actualAuthorDTO);
    }

    @Test
    void findAllTest() {
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Author(), new Author()));
        int actualSize = service.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        AuthorDTO authorDTO = AuthorDTO.builder().id(4).name("New Author").build();
        Author author = Author.builder().id(4).name("New Author").build();
        //when
        when(modelMapper.map(authorDTO, Author.class)).thenReturn(author);
        when(modelMapper.map(author, AuthorDTO.class)).thenReturn(authorDTO);
        when(repository.save(author)).thenReturn(author);
        AuthorDTO actualAuthorDTO = service.create(authorDTO);
        //then
        assertAll(() -> assertEquals(author.getId(), actualAuthorDTO.getId()),
                () -> assertEquals(author.getName(), actualAuthorDTO.getName()));
    }

    @Test
    void createAllTest() {
        //given
        List<AuthorDTO> listDTO = new ArrayList<>() {{
            add(AuthorDTO.builder().id(4).name("First Author").build());
            add(AuthorDTO.builder().id(5).name("Second Author").build());
        }};
        when(mockAuthorsList.get(0)).thenReturn(listDTO.get(0));
        when(mockAuthorsList.get(1)).thenReturn(listDTO.get(1));
        List<AuthorDTO> createList = new ArrayList<>() {{
            add(mockAuthorsList.get(0));
            add(mockAuthorsList.get(1));
        }};
        List<AuthorDTO> authorDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), authorDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getName(), authorDTOList.get(0).getName()),
                () -> assertEquals(createList.get(1).getId(), authorDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getName(), authorDTOList.get(1).getName()));
    }

    @Test
    void updateTest() {
        //given
        int authorId = 1;
        String newName = "Updated Author";
        AuthorDTO authorDTO = AuthorDTO.builder().id(authorId).name(newName).build();
        Author author = Author.builder().id(authorId).name(newName).build();
        //when
        when(repository.getById(authorId)).thenReturn(author);
        when(modelMapper.map(author, AuthorDTO.class)).thenReturn(authorDTO);
        when(repository.getById(authorId)).thenReturn(author);
        AuthorDTO actualUpdatedAuthor = service.update(authorDTO);
        // then
        assertAll(() -> assertEquals(authorId, actualUpdatedAuthor.getId()),
                () -> assertEquals(newName, actualUpdatedAuthor.getName())
        );
    }

    @Test
    void deleteByIdTest() {
        int validId = 1;
        Mockito.when(repository.deleteById(validId)).thenReturn(true);

        assertTrue(service.deleteById(validId));
    }
}
