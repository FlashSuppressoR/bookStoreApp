package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.GenreService;
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
@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {

    @MockBean
    private GenreService genreService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findTest() throws Exception {
        //given
        int genreID = 1;
        GenreDTO expectedGenre = GenreDTO.builder().id(genreID).name("Test Genre").build();
        when(genreService.findById(genreID)).thenReturn(expectedGenre);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/genres/find/{id}", genreID)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenre = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedGenre), actualGenre);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        Page<GenreDTO> expectedGenres = genreService.findAll(pageable);
        when(genreService.findAll(pageable)).thenReturn(expectedGenres);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/genres/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenres = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedGenres), actualGenres);
    }

    @Test
    void createTest() throws Exception {
        //given
        GenreDTO expectedGenre = GenreDTO.builder().id(4).name("Test Genre").build();
        when(genreService.create(expectedGenre)).thenReturn(expectedGenre);
        //when
        MvcResult mvcResult = mockMvc.perform(post("/genres/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedGenre)))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenre = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedGenre), actualGenre);
    }

    @Test
    void createAllTest() throws Exception {
        //given
        GenreDTO expectedFirstGenre = GenreDTO.builder().id(4).name("New FirstGenre").build();
        GenreDTO expectedSecondGenre = GenreDTO.builder().id(5).name("New SecondGenre").build();
        List<GenreDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstGenre);
        expectedList.add(expectedSecondGenre);
        when(genreService.createAll(expectedList)).thenReturn(expectedList);
        //when
        List<GenreDTO> actualGenresList = genreService.createAll(expectedList);
        MvcResult mvcResult = mockMvc.perform(post("/genres/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualGenresList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int genreId = 1;
        String updatedName = "Updated Genre";
        GenreDTO expectedGenre = GenreDTO.builder().id(genreId).name(updatedName).build();
        when(genreService.update(expectedGenre)).thenReturn(expectedGenre);
        //when
        MvcResult mvcResult = mockMvc.perform(put("/genres/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedGenre)))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenre = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedGenre), actualGenre);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        when(genreService.deleteById(id)).thenReturn(true);
        //when
        //then
        mockMvc.perform(delete("/genres/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}