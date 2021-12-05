package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.repository.data.CustomerRepository;
import com.flashsuppressor.java.lab.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;
    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Override
    public CustomerDTO findByEmail(String email) {
        Customer customer = repository.findCustomerByEmail(email);
        return convertToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO findById(int id) {
        return convertToCustomerDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pgb) {
        Page<Customer> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToCustomerDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer newCustomer = repository.save(convertToCustomer(customerDTO));
        return convertToCustomerDTO(newCustomer);
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO customerDTO) {
        CustomerDTO newCustomerDTO = null;
        try {
            Customer customer = repository.getById(customerDTO.getId());
            customer.setName(customerDTO.getName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPassword(customerDTO.getPassword());
            customer.setRole(customerDTO.getRole());

            repository.flush();
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
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    private Customer convertToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
