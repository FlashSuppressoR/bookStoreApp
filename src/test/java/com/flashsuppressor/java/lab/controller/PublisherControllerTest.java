package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.service.PublisherService;
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
@WebMvcTest(controllers = PublisherController.class)
class PublisherControllerTest {

    @MockBean
    private PublisherService publisherService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findTest() throws Exception {
        //given
        int publisherID = 1;
        PublisherDTO expectedPublisher = PublisherDTO.builder().id(publisherID).name("Test Publisher").build();
        // when
        when(publisherService.findById(publisherID)).thenReturn(expectedPublisher);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/publishers/find/{id}", publisherID)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualPublisher = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedPublisher), actualPublisher);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        PublisherDTO newPublisher = PublisherDTO.builder().build();
        // when
        List<PublisherDTO> expectedPublishers = Arrays.asList(newPublisher, newPublisher);
        when(publisherService.findAll()).thenReturn(expectedPublishers);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/publishers/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualPublishers = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedPublishers), actualPublishers);
    }

    @Test
    void create() throws Exception {
        //given
        PublisherDTO expectedPublisher = PublisherDTO.builder().id(4).name("Test Publisher").build();
        //when
        when(publisherService.create(expectedPublisher)).thenReturn(expectedPublisher);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/publishers/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedPublisher)))
                .andExpect(status().isOk())
                .andReturn();
        String actualPublisher = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedPublisher), actualPublisher);
    }

    @Test
    void createAll() throws Exception {
        //given
        PublisherDTO expectedFirstPublisher = PublisherDTO.builder().id(4).name("New FirstGenrePublisher").build();
        PublisherDTO expectedSecondPublisher= PublisherDTO.builder().id(5).name("New SecondPublisher").build();
        List<PublisherDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstPublisher);
        expectedList.add(expectedSecondPublisher);
        //when
        when(publisherService.createAll(expectedList)).thenReturn(expectedList);
        List<PublisherDTO> actualPublishersList = publisherService.createAll(expectedList);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/publishers/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualPublishersList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int publisherId = 1;
        String updatedName = "Updated Name";
        PublisherDTO expectedPublisher = PublisherDTO.builder().id(publisherId).name(updatedName).build();
        //when
        when(publisherService.update(expectedPublisher)).thenReturn(expectedPublisher);
        // then
        MvcResult mvcResult = mockMvc.perform(put("/publishers/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedPublisher)))
                .andExpect(status().isOk())
                .andReturn();
        String actualPublisher = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedPublisher), actualPublisher);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        // when
        when(publisherService.deleteById(id)).thenReturn(true);
        //then
        mockMvc.perform(delete("/publishers/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}