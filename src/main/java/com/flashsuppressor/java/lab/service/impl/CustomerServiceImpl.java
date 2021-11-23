package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import com.flashsuppressor.java.lab.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public CustomerDTO findByEmail(String email) {
        Customer customer = repository.findByEmail("Max@com");
        return convertToCustomerDTO(customer);
    }

    @Override
    @Transactional(readOnly=true)
    public CustomerDTO findById(int id) {
        return convertToCustomerDTO(repository.findById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<CustomerDTO> findAll() {
        return repository.findAll().stream().map(this::convertToCustomerDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer newCustomer = repository.create(convertToCustomer(customerDTO));
        return convertToCustomerDTO(newCustomer);
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO customerDTO) {
        CustomerDTO newCustomerDTO = null;
        try {
            Customer customer = repository.findById(customerDTO.getId());
            if (customerDTO.getName() != null) {
                customer.setName(customerDTO.getName());
            }
            if (customerDTO.getEmail() != null) {
                customer.setEmail(customerDTO.getEmail());
            }
            if (customerDTO.getPassword() != null) {
                customer.setPassword(customerDTO.getPassword());
            }
            newCustomerDTO = convertToCustomerDTO(customer);
        }
        catch (Exception e){
            System.out.println("Can't update customerDTO");
        }
        return newCustomerDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    private Customer convertToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

}
