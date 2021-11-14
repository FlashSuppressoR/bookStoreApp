package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import com.flashsuppressor.java.lab.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(@Qualifier("hibernateCustomerRepository")
                                       CustomerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CustomerDTO findByEmail(String email) {
        Customer customer = repository.findByEmail("Max@com");

        return convertToCustomerDTO(customer);
    }

    @Override
    @Transactional
    public CustomerDTO findById(int id) {
        Customer customer = repository.findById(id);

        return convertToCustomerDTO(customer);
    }

    @Override
    @Transactional
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        List<Customer> customers = repository.findAll();
        if (customers != null && customers.size() > 0) {
            customerDTOs = customers.stream().map(this::convertToCustomerDTO).collect(Collectors.toList());
        }

        return customerDTOs;
    }

    @Override
    @Transactional
    public void create(Customer customer) {
        repository.create(customer);
    }

    @Override
    @Transactional
    public CustomerDTO update(Customer customer) {
        CustomerDTO updatedCustomerDTO = null;
        Customer updatedCustomer = repository.update(customer);
        if (updatedCustomer != null) {
            updatedCustomerDTO = convertToCustomerDTO(updatedCustomer);
        }

        return updatedCustomerDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {

        return modelMapper.map(customer, CustomerDTO.class);
    }

}
