package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Pageable bookPageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findTest() throws Exception {
        //given
        long bookId = 4;
        BookDTO expectedBook = BookDTO.builder().id(4).name("New Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build();
        // when
        when(bookService.findById(bookId)).thenReturn(expectedBook);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/books/find/{id}", bookId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedBook), actualBook);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        Page<BookDTO> expectedBooks = bookService.findAll(bookPageable);
        // when
        when(bookService.findAll(bookPageable)).thenReturn(expectedBooks);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/books/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedBooks), actualBook);
    }

    @Test
    void create() throws Exception {
        //given
        BookDTO expectedBook = BookDTO.builder().id(4).name("New Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build();
        //when
        when(bookService.create(expectedBook)).thenReturn(expectedBook);
        BookDTO actualBookDTO = bookService.create(expectedBook);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/books/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualBookDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedBook), actualBook);
    }

    @Test
    void createAll() throws Exception {
        //given
        BookDTO expectedFirstBook = BookDTO.builder().id(4).name("New FirstBook").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need asd").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build();
        BookDTO expectedSecondBook = BookDTO.builder().id(5).name("New SecondBook").price(123)
                .publisherDTO(PublisherDTO.builder().id(5).name("Need For Speed").build())
                .genreDTO(GenreDTO.builder().id(4).name("Dee dEw").build()).amount(1).build();
        List<BookDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstBook);
        expectedList.add(expectedSecondBook);
        //when
        when(bookService.createAll(expectedList)).thenReturn(expectedList);
        List<BookDTO> actualBooksList = bookService.createAll(expectedList);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/books/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualBooksList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        long bookId = 1;
        String updatedName = "Updated Book";
        BookDTO expectedBook = BookDTO.builder().id(bookId).name(updatedName).price(13)
                .publisherDTO(PublisherDTO.builder().id(4).name("New FirstGenrePublisher").build())
                .genreDTO(GenreDTO.builder().id(4).name("New FirstGenre").build()).amount(2).build();
        //when
        when(bookService.update(expectedBook)).thenReturn(expectedBook);
        // then
        MvcResult mvcResult = mockMvc.perform(put("/books/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedBook)))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedBook), actualBook);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        long id = 1;
        // when
        when(bookService.deleteById(id)).thenReturn(true);
        //then
        mockMvc.perform(delete("/books/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}