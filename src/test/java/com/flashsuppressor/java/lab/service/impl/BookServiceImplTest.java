package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.data.BookRepository;
import com.flashsuppressor.java.lab.service.BookService;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
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
public class BookServiceImplTest {

    @InjectMocks
    private BookService service;
    @Mock
    private BookRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private List<BookDTO> mockBooksList;

    private final Pageable bookPageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findByIdTest() {
        //given
        long bookID = 1;
        Book book = Book.builder().id(bookID).name("TestBook").build();
        BookDTO expectedABookDTO = BookDTO.builder().id(bookID).name("TestBook").price(123)
                .publisherId(1).genreId(1).amount(1).build();
        when(repository.getById(bookID)).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(expectedABookDTO);
        //when
        BookDTO actualBookDTO = service.findById(bookID);
        //then
        assertEquals(expectedABookDTO, actualBookDTO);
    }

    @Test
    void findAllTest() {
        //given
        int expectedSize = 2;
        //when
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Book(), new Book()));
        int actualSize = service.findAll(bookPageable).getSize();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        BookDTO bookDTO = BookDTO.builder().id(4).name("New Book").price(123)
                .publisherId(1).amount(1).build();
        Book book = Book.builder().id(4L).name("New Book").price(123)
                .publisher(Publisher.builder().id(4).name("Need For Speed").build())
                .genre(Genre.builder().id(4).name("Soe Ew").build()).amount(1).build();
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        when(repository.save(book)).thenReturn(book);
        //when
        BookDTO actualBookDTO = service.create(bookDTO);
        //then
        assertAll(() -> assertEquals(book.getId(), actualBookDTO.getId()),
                () -> assertEquals(actualBookDTO.getName(), actualBookDTO.getName()));
    }

    @Test
    void createAllTest() {
        //given
        List<BookDTO> listDTO = new ArrayList<>() {{
            add(BookDTO.builder().id(4L).name("First Book").price(123)
                    .publisherId(1).amount(1).build());
            add(BookDTO.builder().id(5L).name("Second Author").price(123)
                    .publisherId(2).amount(1).build());
        }};
        when(mockBooksList.get(0)).thenReturn(listDTO.get(0));
        when(mockBooksList.get(1)).thenReturn(listDTO.get(1));
        //when
        List<BookDTO> createList = new ArrayList<>() {{
            add(mockBooksList.get(0));
            add(mockBooksList.get(1));
        }};
        List<BookDTO> bookDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), bookDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getName(), bookDTOList.get(0).getName()),
                () -> assertEquals(createList.get(0).getAmount(), bookDTOList.get(0).getAmount()),
                () -> assertEquals(createList.get(1).getId(), bookDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getName(), bookDTOList.get(1).getName()),
                () -> assertEquals(createList.get(1).getAmount(), bookDTOList.get(1).getAmount()));
    }

    @Test
    void updateTest() {
        //given
        long bookId = 1;
        String newName = "Updated Book";
        BookDTO bookDTO = BookDTO.builder().id(bookId).name(newName).build();
        Book book = Book.builder().id(bookId).name(newName).build();
        when(repository.getById(bookId)).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        when(repository.getById(bookId)).thenReturn(book);
        //when
        BookDTO actualUpdatedBook = service.update(bookDTO);
        // then
        assertAll(() -> assertEquals(bookId, actualUpdatedBook.getId()),
                () -> assertEquals(newName, actualUpdatedBook.getName())
        );
    }

    @Test
    void deleteByIdTest() {
        //given
        long validId = 1;
        //when
        repository.deleteById(validId);
        //then
        assertTrue(repository.findById(validId).isEmpty());
    }
}
