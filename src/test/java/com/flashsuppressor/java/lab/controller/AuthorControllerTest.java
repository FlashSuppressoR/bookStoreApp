package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthorController.class)
class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findTest() throws Exception {
        //given
        int authorId = 1;
        AuthorDTO expectedAuthor = AuthorDTO.builder().id(authorId).name("Bred Dee").build();
        // when
        when(authorService.findById(authorId)).thenReturn(expectedAuthor);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/authors/find/{id}", authorId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualAuthor = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedAuthor), actualAuthor);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        AuthorDTO authorDTO = AuthorDTO.builder().build();
        // when
        List<AuthorDTO> expectedAuthors = Arrays.asList(authorDTO, authorDTO);
        when(authorService.findAll()).thenReturn(expectedAuthors);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/authors/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualAuthors = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedAuthors), actualAuthors);
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
        String actualAuthor = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedAuthor), actualAuthor);
    }

    @Test
    void createAll() throws Exception {
        //given
        AuthorDTO expectedFirstAuthor = AuthorDTO.builder().id(4).name("New FirstAuthor").build();
        AuthorDTO expectedSecondAuthor = AuthorDTO.builder().id(5).name("New SecondAuthor").build();
        List<AuthorDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstAuthor);
        expectedList.add(expectedSecondAuthor);
        //when
        when(authorService.createAll(expectedList)).thenReturn(expectedList);
        List<AuthorDTO> actualAuthorsList = authorService.createAll(expectedList);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/authors/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualAuthorsList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int userId = 1;
        String updatedName = "Updated Author";
        AuthorDTO expectedAuthor = AuthorDTO.builder().id(userId).name(updatedName).build();
        //when
        when(authorService.update(expectedAuthor)).thenReturn(expectedAuthor);
        // then
        MvcResult mvcResult = mockMvc.perform(put("/authors/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedAuthor)))
                .andExpect(status().isOk())
                .andReturn();
        String actualAuthor = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedAuthor), actualAuthor);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        // when
        when(authorService.deleteById(id)).thenReturn(true);
        //then
        mockMvc.perform(delete("/authors/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}