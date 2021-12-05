package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.service.dto.AuthorDTO;
import com.flashsuppressor.java.lab.repository.data.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findByIdTest() {
        //given
        int authorID = 1;
        Author author = Author.builder().id(authorID).name("TestAuthor").build();
        AuthorDTO expectedAuthorDTO = AuthorDTO.builder().id(authorID).name("TestAuthor").build();
        //when
        when(repository.getById(authorID)).thenReturn(author);
        when(modelMapper.map(author, AuthorDTO.class)).thenReturn(expectedAuthorDTO);
        AuthorDTO actualAuthorDTO = service.findById(authorID);
        //then
        assertEquals(expectedAuthorDTO, actualAuthorDTO);
    }

    @Test
    void findAllTest() {
        //given
        int expectedSize = 2;
        //when
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Author(), new Author()));
        int actualSize = service.findAll(pageable).getSize();
        //then
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
        List<AuthorDTO> expectedListDto = new ArrayList<>() {{
            add(AuthorDTO.builder().id(4).name("First Author").build());
            add(AuthorDTO.builder().id(5).name("Second Author").build());
        }};
        List<Author> list = new ArrayList<>() {{
            add(Author.builder().id(4).name("First Author").build());
            add(Author.builder().id(5).name("Second Author").build());
        }};
        //when
        when(modelMapper.map(expectedListDto.get(0), Author.class)).thenReturn(list.get(0));
        when(modelMapper.map(expectedListDto.get(1), Author.class)).thenReturn(list.get(1));
        when(modelMapper.map(list.get(0), AuthorDTO.class)).thenReturn(expectedListDto.get(0));
        when(modelMapper.map(list.get(1), AuthorDTO.class)).thenReturn(expectedListDto.get(1));
        when(repository.save(list.get(0))).thenReturn(list.get(0));
        when(repository.save(list.get(1))).thenReturn(list.get(1));
        List<AuthorDTO> actualListDto = service.createAll(expectedListDto);
        //then
        assertAll(() -> assertEquals(expectedListDto.get(0).getId(), actualListDto.get(0).getId()),
                () -> assertEquals(expectedListDto.get(0).getName(), actualListDto.get(0).getName()),
                () -> assertEquals(expectedListDto.get(1).getId(), actualListDto.get(1).getId()),
                () -> assertEquals(expectedListDto.get(1).getName(), actualListDto.get(1).getName()));
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
        //then
        assertAll(() -> assertEquals(authorId, actualUpdatedAuthor.getId()),
                () -> assertEquals(newName, actualUpdatedAuthor.getName())
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
