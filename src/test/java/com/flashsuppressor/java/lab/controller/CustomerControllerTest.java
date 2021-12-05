package com.flashsuppressor.java.lab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.CustomerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("email"));

    @Test
    void findTest() throws Exception {
        //given
        int customerId = 4;
        CustomerDTO expectedCustomer = CustomerDTO.builder()
                .id(customerId).name("Alex").email("test@com").password("abc").build();
        when(customerService.findById(customerId)).thenReturn(expectedCustomer);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/customers/find/{id}", customerId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualCustomer = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedCustomer), actualCustomer);
    }

    @Test
    void findAllTest() throws Exception {
        //given
        Page<CustomerDTO> expectedCustomers = customerService.findAll(pageable);
        when(customerService.findAll(pageable)).thenReturn(expectedCustomers);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/customers/find/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualCustomers = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedCustomers), actualCustomers);
    }

    @Test
    void createTest() throws Exception {
        //given
        CustomerDTO expectedCustomer = CustomerDTO.builder()
                .id(4).name("Alex").email("test@com").password("abc").build();
        when(customerService.create(expectedCustomer)).thenReturn(expectedCustomer);
        //when
        CustomerDTO actualCustomerDTO = customerService.create(expectedCustomer);
        MvcResult mvcResult = mockMvc.perform(post("/customers/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actualCustomerDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String actualCustomer = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedCustomer), actualCustomer);
    }

    @Test
    void updateTest() throws Exception {
        //given
        int customerId = 4;
        String newCustomerName = "Roy";
        CustomerDTO expectedCustomer = CustomerDTO.builder()
                .id(customerId).name(newCustomerName).email("test@com").password("abc").build();
        when(customerService.update(expectedCustomer)).thenReturn(expectedCustomer);
        //when
        MvcResult mvcResult = mockMvc.perform(put("/customers/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expectedCustomer)))
                .andExpect(status().isOk())
                .andReturn();
        String actualCustomer = mvcResult.getResponse().getContentAsString();
        //then
        assertEquals(objectMapper.writeValueAsString(expectedCustomer), actualCustomer);
    }

    @Test
    void deleteByID_thenReturns200() throws Exception {
        //given
        int id = 1;
        when(customerService.deleteById(id)).thenReturn(true);
        //when
        //then
        mockMvc.perform(delete("/customers/delete/{id}", id)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }
}