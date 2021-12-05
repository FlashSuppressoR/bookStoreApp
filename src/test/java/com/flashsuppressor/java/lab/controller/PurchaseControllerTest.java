package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.service.PurchaseService;
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

import java.sql.Timestamp;
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
@WebMvcTest(controllers = PurchaseController.class)
class PurchaseControllerTest {

    @MockBean
    private PurchaseService purchaseService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Pageable pageable = PageRequest.of(1, 10, Sort.by("customer_id"));

    @Test
    void findTest() throws Exception {
        //given
        int purchaseID = 1;
        PurchaseDTO expectedPurchase = PurchaseDTO.builder().id(purchaseID).customerDTO(CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        when(purchaseService.findById(purchaseID)).thenReturn(expectedPurchase);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/purchases/find/{id}", purchaseID)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualPurchase = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedPurchase), actualPurchase);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        Page<PurchaseDTO> expectedPurchases = purchaseService.findAll(pageable);
        when(purchaseService.findAll(pageable)).thenReturn(expectedPurchases);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/purchases/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualPurchases = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedPurchases), actualPurchases);
    }

    @Test
    void createTest() throws Exception {
        //given
        int purchaseID = 1;
        PurchaseDTO expectedPurchase = PurchaseDTO.builder().id(purchaseID).customerDTO(CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        when(purchaseService.create(expectedPurchase)).thenReturn(expectedPurchase);
        //when
        MvcResult mvcResult = mockMvc.perform(post("/purchases/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedPurchase)))
                .andExpect(status().isOk())
                .andReturn();
        String actualPurchase = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedPurchase), actualPurchase);
    }

    @Test
    void createAllTest() throws Exception {
        //given
        PurchaseDTO expectedFirstPurchase = PurchaseDTO.builder().id(4).customerDTO(CustomerDTO.builder()
                .id(4).name("Al Sdz").email("Shez@com").password("ax").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        PurchaseDTO expectedSecondPurchase = PurchaseDTO.builder().id(5).customerDTO(CustomerDTO.builder()
                .id(5).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        List<PurchaseDTO> expectedList = new ArrayList<>();
        expectedList.add(expectedFirstPurchase);
        expectedList.add(expectedSecondPurchase);
        when(purchaseService.createAll(expectedList)).thenReturn(expectedList);
        //when
        MvcResult mvcResult = mockMvc.perform(post("/purchases/create/all")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedList)))
                .andExpect(status().isOk())
                .andReturn();
        String actualList = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedList), actualList);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int purchaseId = 1;
        String updatedTime = "2017-12-10 02:33:00.0";
        PurchaseDTO expectedPurchase = PurchaseDTO.builder().id(purchaseId).customerDTO(CustomerDTO.builder()
                .id(4).name("Al Sdz").email("Shez@com").password("ax").build())
                .purchaseTime(Timestamp.valueOf(updatedTime)).build();
        when(purchaseService.update(expectedPurchase)).thenReturn(expectedPurchase);
        //when
        MvcResult mvcResult = mockMvc.perform(put("/purchases/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedPurchase)))
                .andExpect(status().isOk())
                .andReturn();
        String actualPurchase = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedPurchase), actualPurchase);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        when(purchaseService.deleteById(id)).thenReturn(true);
        //when
        //then
        mockMvc.perform(delete("/purchases/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}