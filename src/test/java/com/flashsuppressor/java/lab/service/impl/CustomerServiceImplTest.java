package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.repository.data.CustomerRepository;
import com.flashsuppressor.java.lab.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerService service;
    @Mock
    private CustomerRepository repository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void findByIdTest() {
        int customerID = 1;
        Customer customer = Customer.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build();
        CustomerDTO expectedCustomerDTO = CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build();

        when(repository.getById(customerID)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(expectedCustomerDTO);
        CustomerDTO actualCustomerDTO = service.findById(customerID);

        assertEquals(expectedCustomerDTO, actualCustomerDTO);
    }

    @Test
    void findAllTest() {
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        int actualSize = service.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        Customer customer = Customer.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build();
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build();
        //when
        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);
        when(repository.save(customer)).thenReturn(customer);
        CustomerDTO actualCustomerDTO = service.create(customerDTO);
        //then
        assertAll(() -> assertEquals(customerDTO.getId(), actualCustomerDTO.getId()),
                () -> assertEquals(customerDTO.getName(), actualCustomerDTO.getName()),
                () -> assertEquals(customerDTO.getEmail(), actualCustomerDTO.getEmail()),
                () -> assertEquals(customerDTO.getPassword(), actualCustomerDTO.getPassword()));
    }

    @Test
    void updateTest() {
        //given
        int customerId = 1;
        String updatedName = "New Name";
        Customer customer = Customer.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build();
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build();
        //when
        when(repository.getById(customerId)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);
        when(repository.getById(customerId)).thenReturn(customer);
        CustomerDTO actualUpdatedCustomer = service.update(customerDTO);
        // then
        assertAll(() -> assertEquals(customerId, actualUpdatedCustomer.getId()),
                () -> assertEquals(updatedName, actualUpdatedCustomer.getName())
        );
    }

    @Test
    void deleteByIdTest() {
        int validId = 1;
        Mockito.when(repository.deleteById(validId)).thenReturn(true);

        assertTrue(service.deleteById(validId));
    }
}
