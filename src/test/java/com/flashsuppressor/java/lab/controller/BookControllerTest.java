package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.service.BookService;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
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
                .publisherId(1).genreId(1).amount(1).build();
        when(bookService.findById(bookId)).thenReturn(expectedBook);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/books/find/{id}", bookId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedBook), actualBook);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        Page<BookDTO> expectedBooks = bookService.findAll(bookPageable);
        when(bookService.findAll(bookPageable)).thenReturn(expectedBooks);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/books/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedBooks), actualBook);
    }

    @Test
    void createTest() throws Exception {
        //given
        BookDTO expectedBook = BookDTO.builder().id(4).name("New Book").price(123)
                .publisherId(1).genreId(1).amount(1).build();
        when(bookService.create(expectedBook)).thenReturn(expectedBook);
        //when
        BookDTO actualBookDTO = bookService.create(expectedBook);
        MvcResult mvcResult = mockMvc.perform(post("/books/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualBookDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedBook), actualBook);
    }

    @Test
    void createAllTest() throws Exception {
        //given
        BookDTO expectedFirstBook = BookDTO.builder().id(4).name("New First Book").price(123)
                .publisherId(1).genreId(1).amount(1).build();
        BookDTO expectedSecondBook = BookDTO.builder().id(5).name("New Second Book").price(16)
                .publisherId(2).genreId(2).amount(1).build();
        List<BookDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstBook);
        expectedList.add(expectedSecondBook);
        when(bookService.createAll(expectedList)).thenReturn(expectedList);
        //when
        List<BookDTO> actualBooksList = bookService.createAll(expectedList);
        MvcResult mvcResult = mockMvc.perform(post("/books/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualBooksList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        long bookId = 1;
        String updatedName = "Updated Book";
        BookDTO expectedBook = BookDTO.builder().id(4).name(updatedName).price(123)
                .publisherId(1).genreId(1).amount(1).build();
        when(bookService.update(expectedBook)).thenReturn(expectedBook);
        //when
        MvcResult mvcResult = mockMvc.perform(put("/books/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedBook)))
                .andExpect(status().isOk())
                .andReturn();
        String actualBook = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedBook), actualBook);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        long id = 1;
        when(bookService.deleteById(id)).thenReturn(true);
        //when
        //then
        mockMvc.perform(delete("/books/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}