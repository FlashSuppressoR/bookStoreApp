package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.entity.dto.CartDTO;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
@WebMvcTest(controllers = CartController.class)
class CartControllerTest {

    @MockBean
    private CartService cartService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findTest() throws Exception {
        //given
        int cartId = 4;
        CartDTO expectedCart = CartDTO.builder().id(4).customerDTO
                (CustomerDTO.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .bookId(4L).bookCounter(1).build();
        // when
        when(cartService.findById(cartId)).thenReturn(expectedCart);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/carts/find/{id}", cartId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualCart = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedCart),actualCart);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        CartDTO cartDTO = CartDTO.builder().build();
        // when
        List<CartDTO> expectedCarts = Arrays.asList(cartDTO, cartDTO);
        when(cartService.findAll()).thenReturn(expectedCarts);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/carts/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualCarts = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedCarts), actualCarts);
    }

    @Test
    void create() throws Exception {
        //given
        CartDTO expectedCart = CartDTO.builder().id(4).customerDTO
                (CustomerDTO.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .bookId(4L).bookCounter(1).build();
        //when
        when(cartService.create(expectedCart)).thenReturn(expectedCart);
        CartDTO actualCartDTO = cartService.create(expectedCart);
        //then
        MvcResult mvcResult = mockMvc.perform(post("/carts/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualCartDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String actualCart = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedCart), actualCart);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int cartId = 1;
        int bookCounter = 3;
        CartDTO expectedCart = CartDTO.builder().id(cartId).customerDTO
                (CustomerDTO.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .bookId(4L).bookCounter(bookCounter).build();
        //when
        when(cartService.update(expectedCart)).thenReturn(expectedCart);
        // then
        MvcResult mvcResult = mockMvc.perform(put("/carts/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedCart)))
                .andExpect(status().isOk())
                .andReturn();
        String actualCart = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedCart), actualCart);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        // when
        when(cartService.deleteById(id)).thenReturn(true);
        //then
        mockMvc.perform(delete("/carts/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}