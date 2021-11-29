package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthorController.class)
class AuthorControllerTest {

    @MockBean
    AuthorService authorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void find() throws Exception {
        //given
        int authorId = 1;
        AuthorDTO expectedAuthor = AuthorDTO.builder().id(authorId).name("First Author").build();
        // when
        when(authorService.findById(authorId)).thenReturn(expectedAuthor);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/authors/{id}", authorId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualAuthor = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedAuthor), actualAuthor);
    }

    @Test
    void findAll() throws Exception {
        //given
        AuthorDTO authorDTO = AuthorDTO.builder().build();
        // when
        List<AuthorDTO> expectedResponseBody = Arrays.asList(authorDTO, authorDTO);
        when(authorService.findAll()).thenReturn(expectedResponseBody);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/authors/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void create() throws Exception {
        //given
        AuthorDTO expectedAuthor = AuthorDTO.builder().id(4).name("New Author").build();
        //when
        when(authorService.create(expectedAuthor)).thenReturn(expectedAuthor);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/authors/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedAuthor)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedAuthor), actualResponseBody);
    }

    @Test
    void createAll() throws Exception {
        //given
        AuthorDTO expectedFirstAuthor = AuthorDTO.builder().id(4).name("New FirstAuthor").build();
        AuthorDTO expectedSecondAuthor = AuthorDTO.builder().id(5).name("New FirstAuthor").build();
        List<AuthorDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstAuthor);
        expectedList.add(expectedSecondAuthor);
        List<AuthorDTO> actualList = new ArrayList<>();
        //when
        when(authorService.createAll(actualList)).thenReturn(expectedList);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/authors/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualListDTO = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedList), actualListDTO);
    }

    @Test
    void update() throws Exception {
        //given
        int userId = 1;
        String name = "Updated Author";
        AuthorDTO expectedResponseBody = AuthorDTO.builder().id(userId).name(name).build();
        //when
        when(authorService.update(expectedResponseBody)).thenReturn(expectedResponseBody);
        // then
        MvcResult mvcResult = mockMvc.perform(put("/authors/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void deleteByID() throws Exception {
        //given
        int id = 1;
        // when
        when(authorService.deleteById(id)).thenReturn(true);
        //then
        mockMvc.perform(delete("/authors/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}