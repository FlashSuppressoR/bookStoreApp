package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import com.flashsuppressor.java.lab.service.dto.ReviewDTO;
import com.flashsuppressor.java.lab.service.ReviewService;
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
@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Pageable pageable = PageRequest.of(1, 10, Sort.by("customer_id"));

    @Test
    void findTest() throws Exception {
        //given
        int reviewID = 1;
        ReviewDTO expectedReview = ReviewDTO.builder().id(reviewID).mark(4).comment("Norm").bookDTO(BookDTO.builder().id(4).name("New Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).build();
        when(reviewService.findById(reviewID)).thenReturn(expectedReview);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/reviews/find/{id}", reviewID)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualReview = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedReview), actualReview);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        Page<ReviewDTO> expectedReviews = reviewService.findAll(pageable);
        when(reviewService.findAll(pageable)).thenReturn(expectedReviews);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/reviews/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedReviews), actualList);
    }

    @Test
    void createTest() throws Exception {
        //given
        ReviewDTO expectedReview = ReviewDTO.builder().id(4).mark(4).comment("Norm").bookDTO(BookDTO.builder().id(4).name("New Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).build();
        when(reviewService.create(expectedReview)).thenReturn(expectedReview);
        //when
        MvcResult mvcResult = mockMvc.perform(post("/reviews/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedReview)))
                .andExpect(status().isOk())
                .andReturn();
        String actualReview = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedReview), actualReview);
    }

    @Test
    void createAllTest() throws Exception {
        //given
        ReviewDTO expectedFirstReview = ReviewDTO.builder().id(4).mark(4).comment("Good").bookDTO(BookDTO.builder().id(4).name("New Super Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need more...").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).build();
        ReviewDTO expectedSecondReview = ReviewDTO.builder().id(5).mark(4).comment("Norm").bookDTO(BookDTO.builder().id(4).name("New Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(5).name("Need For Speed").build())
                .genreDTO(GenreDTO.builder().id(5).name("Soe EEw").build()).amount(2).build()).build();
        List<ReviewDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstReview);
        expectedList.add(expectedSecondReview);
        when(reviewService.createAll(expectedList)).thenReturn(expectedList);
        //when
        List<ReviewDTO> actualReviewList = reviewService.createAll(expectedList);
        MvcResult mvcResult = mockMvc.perform(post("/reviews/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualReviewList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int reviewId = 1;
        String updatedComment = "Good job (No)";
        ReviewDTO expectedReview = ReviewDTO.builder().id(4).mark(4).comment(updatedComment).bookDTO(BookDTO.builder().id(4).name("New Super Book").price(123)
                .publisherDTO(PublisherDTO.builder().id(4).name("Need more...").build())
                .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).build();
        when(reviewService.update(expectedReview)).thenReturn(expectedReview);
        //when
        MvcResult mvcResult = mockMvc.perform(put("/reviews/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedReview)))
                .andExpect(status().isOk())
                .andReturn();
        String actualReview = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedReview), actualReview);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        when(reviewService.deleteById(id)).thenReturn(true);
        //when
        //then
        mockMvc.perform(delete("/reviews/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}