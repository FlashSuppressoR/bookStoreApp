package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO findByEmail(String email);

    CustomerDTO findById(int id);

    List<CustomerDTO> findAll();

    void create(Customer customer);

    CustomerDTO update(Customer customer);

    boolean deleteById(int id);
}
