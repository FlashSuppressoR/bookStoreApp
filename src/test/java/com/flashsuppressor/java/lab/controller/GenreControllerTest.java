package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.GenreService;
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
@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {

    @MockBean
    private GenreService genreService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findTest() throws Exception {
        //given
        int genreID = 1;
        GenreDTO expectedGenre = GenreDTO.builder().id(genreID).name("Test Genre").build();
        // when
        when(genreService.findById(genreID)).thenReturn(expectedGenre);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/genres/find/{id}", genreID)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenre = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedGenre), actualGenre);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        GenreDTO genreDTO = GenreDTO.builder().build();
        // when
        List<GenreDTO> expectedGenres = Arrays.asList(genreDTO, genreDTO);
        when(genreService.findAll()).thenReturn(expectedGenres);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/genres/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenres = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedGenres), actualGenres);
    }

    @Test
    void create() throws Exception {
        //given
        GenreDTO expectedGenre = GenreDTO.builder().id(4).name("Test Genre").build();
        //when
        when(genreService.create(expectedGenre)).thenReturn(expectedGenre);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/genres/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedGenre)))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenre = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedGenre), actualGenre);
    }

    @Test
    void createAll() throws Exception {
        //given
        GenreDTO expectedFirstGenre = GenreDTO.builder().id(4).name("New FirstGenre").build();
        GenreDTO expectedSecondGenre = GenreDTO.builder().id(5).name("New SecondGenre").build();
        List<GenreDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstGenre);
        expectedList.add(expectedSecondGenre);
        //when
        when(genreService.createAll(expectedList)).thenReturn(expectedList);
        List<GenreDTO> actualGenresList = genreService.createAll(expectedList);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/genres/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualGenresList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int genreId = 1;
        String updatedName = "Updated Genre";
        GenreDTO expectedGenre = GenreDTO.builder().id(genreId).name(updatedName).build();
        //when
        when(genreService.update(expectedGenre)).thenReturn(expectedGenre);
        // then
        MvcResult mvcResult = mockMvc.perform(put("/genres/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedGenre)))
                .andExpect(status().isOk())
                .andReturn();
        String actualGenre = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedGenre), actualGenre);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        // when
        when(genreService.deleteById(id)).thenReturn(true);
        //then
        mockMvc.perform(delete("/genres/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}